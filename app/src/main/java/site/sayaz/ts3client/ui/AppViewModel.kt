package site.sayaz.ts3client.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import site.sayaz.ts3client.audio.AudioInput
import site.sayaz.ts3client.audio.AudioPlayer
import site.sayaz.ts3client.audio.AudioRecorder
import site.sayaz.ts3client.client.ClientSocket
import site.sayaz.ts3client.client.IdentityDataDao
import site.sayaz.ts3client.client.Listener
import site.sayaz.ts3client.client.ServerData
import site.sayaz.ts3client.client.ServerDataDao
import site.sayaz.ts3client.audio.AudioService
import site.sayaz.ts3client.audio.AudioServiceConnection
import site.sayaz.ts3client.ui.server.ConnectionState
import site.sayaz.ts3client.ui.server.ServerConnectionState
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val identityDataDao: IdentityDataDao, val serverDataDao: ServerDataDao, application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()

    //Create audio service
    private val audioIntent: Intent
    private val ts3Listener = Listener({ updateChannelList() }, { updateClientList() })
    private val audioServiceConnection : AudioServiceConnection
    private lateinit var clientSockets : ClientSocket

    init {
        Log.d("AppViewModel", "init viewmodel")
        viewModelScope.launch {
            val serversFromDb = serverDataDao.getAll()
            if (serversFromDb.isNotEmpty()) {
                _uiState.value =
                    AppState(servers = serversFromDb, serverConnectionStates = serversFromDb.map {
                        ServerConnectionState(
                            it.id, ConnectionState.NOT_CONNECTED
                        )
                    })
            }
        }
        audioIntent = Intent(application, AudioService::class.java)
        audioServiceConnection = AudioServiceConnection()
        application.bindService(audioIntent, audioServiceConnection, Context.BIND_AUTO_CREATE)
        viewModelScope.launch {
            while (!audioServiceConnection.isBound) {
                delay(100) // Wait for the service to be bound
            }
            val audioRecorder = audioServiceConnection.audioService?.audioRecorder
            val audioPlayer = audioServiceConnection.audioService?.audioPlayer
            if (audioRecorder == null || audioPlayer == null) {
                throw Exception("Failed to create audio service")
            }
            Log.d("AppViewModel", "Audio service created")
            clientSockets = ClientSocket(identityDataDao, audioRecorder, audioPlayer, ts3Listener)
        }
    }


    fun addServer(server: ServerData) {
        _uiState.update {
            viewModelScope.launch { serverDataDao.insert(server) }
            it.copy(
                servers = it.servers + server,
                serverConnectionStates = it.serverConnectionStates + ServerConnectionState(
                    server.id, ConnectionState.NOT_CONNECTED
                )
            )
        }
    }

    /**
     * Connect to the server and get the channel list
     * if success will start a foreground service
     * @param server: ServerData
     * @throws Exception
     */
    fun connectServer(server: ServerData) {
        viewModelScope.launch {
            try {
                lockInConnect(true)
                withContext(Dispatchers.IO) {
                    updateServerConnectionState(server.id, ConnectionState.CONNECTING)
                    clientSockets.connect(server)
                    // Connection successful
                    onConnectSuccess(server)
                }
            } catch (e: Exception) {
                showErrorMessage(e.message)
                updateServerConnectionState(server.id, ConnectionState.ERROR)
                lockInConnect(false)
                e.printStackTrace()
            }
        }
    }

    private fun onConnectSuccess(server: ServerData) {
        viewModelScope.launch {
            try {
                // get channel list
                updateChannelList()
                updateClientList()
                updateServerConnectionState(server.id, ConnectionState.CONNECTED)
                // Start the audio service
                getApplication<Application>().startService(audioIntent)
            } catch (e: Exception) {
                showErrorMessage(e.message)
                e.printStackTrace()
            }
        }
    }

    fun switchChannel(channelID: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    clientSockets.switchChannel(channelID)
                } catch (e: Exception) {
                    Log.i("AppViewModel", "Failed to switch channel:${e.message}")
                    showErrorMessage(e.message)
                }
            }
        }
    }

    /**
     * Disconnect from the server
     */

    fun disconnect() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (clientSockets.isConnected) {
                    clientSockets.disconnect()
                    updateServerConnectionState(
                        clientSockets.serverData.id, ConnectionState.NOT_CONNECTED
                    )
                    lockInConnect(false)
                }
            }
        }
    }

    private fun showErrorMessage(message: String?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    errorMessage = message ?: ""
                )
            }
            delay(1000)
            _uiState.update {
                it.copy(
                    errorMessage = ""
                )
            }
        }
    }

    private fun updateServerConnectionState(serverId: Int, connectionState: ConnectionState) {
        _uiState.update {
            it.copy(serverConnectionStates = it.serverConnectionStates.map {
                if (it.serverId == serverId) {
                    it.copy(connectionState = connectionState)
                } else {
                    it
                }
            })
        }
    }

    private fun lockInConnect(value: Boolean) {
        _uiState.update {
            it.copy(
                isInConnect = value
            )
        }
    }

    private fun updateClientList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val clientList = clientSockets.clientList.toList()
                _uiState.update {
                    it.copy(
                        clients = clientList
                    )
                }
            }
        }
    }

    private fun updateChannelList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val channelList = clientSockets.channelList.toList()
                _uiState.update {
                    it.copy(
                        channels = channelList
                    )
                }
            }
        }
    }
}