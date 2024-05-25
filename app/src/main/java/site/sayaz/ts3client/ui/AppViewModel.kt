package site.sayaz.ts3client.ui

import android.app.Application
import android.content.Context
import android.content.Intent
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
import site.sayaz.ts3client.audio.AudioPlayer
import site.sayaz.ts3client.audio.AudioRecorder
import site.sayaz.ts3client.client.ClientSocket
import site.sayaz.ts3client.client.IdentityDataDao
import site.sayaz.ts3client.client.Listener
import site.sayaz.ts3client.client.ServerData
import site.sayaz.ts3client.client.ServerDataDao
import site.sayaz.ts3client.audio.AudioService
import site.sayaz.ts3client.audio.AudioServiceConnection
import site.sayaz.ts3client.ui.channel.AudioController
import site.sayaz.ts3client.ui.channel.ChannelData
import site.sayaz.ts3client.ui.channel.ChannelStateInterface
import site.sayaz.ts3client.ui.channel.ClientData
import site.sayaz.ts3client.ui.channel.ClientState
import site.sayaz.ts3client.ui.channel.toData
import site.sayaz.ts3client.ui.server.ConnectionState
import site.sayaz.ts3client.ui.server.ServerConnectionState
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val identityDataDao: IdentityDataDao, val serverDataDao: ServerDataDao, application: Application
) : AndroidViewModel(application), ChannelStateInterface,AudioController {

    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()

    //Create audio service
    private val audioIntent: Intent
    private val ts3Listener = Listener(this)
    private val audioServiceConnection: AudioServiceConnection
    private lateinit var clientSockets: ClientSocket

    private lateinit var audioRecorder: AudioRecorder
    private lateinit var audioPlayer: AudioPlayer

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
            audioRecorder = audioServiceConnection.audioService.audioRecorder
            audioPlayer = audioServiceConnection.audioService.audioPlayer
            Log.d("AppViewModel", "Audio service created")
            clientSockets = ClientSocket(identityDataDao, audioRecorder, audioPlayer, ts3Listener)
        }
    }


    fun addServer(server: ServerData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.update {
                    serverDataDao.insert(server)
                    val newServer = serverDataDao.getByHostname(server.hostname)
                    it.copy(
                        servers = it.servers + newServer,
                        serverConnectionStates = it.serverConnectionStates + ServerConnectionState(
                            newServer.id, ConnectionState.NOT_CONNECTED
                        )
                    )
                }
            }
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
        _uiState.update { state ->
            state.copy(serverConnectionStates = state.serverConnectionStates.map {
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

    override fun mute() {
        audioRecorder.mute()
        _uiState.update { state ->
            state.copy(
                isMuted = true,
                clients = state.clients.map {
                    if (it.id == clientSockets.id) {
                        it.copy(state = ClientState.MUTED)
                    } else {
                        it
                    }
                }
            )
        }
    }

    override fun unmute() {
        audioRecorder.unmute()
        _uiState.update { state ->
            state.copy(
                isMuted = false,
                clients = state.clients.map {
                    if (it.id == clientSockets.id) {
                        it.copy(state = ClientState.SILENT)
                    } else {
                        it
                    }
                }
            )
        }
    }

    override fun deafen() {
        audioPlayer.deafen()
        _uiState.update { state ->
            state.copy(
                isDeafened = true
            )
        }
    }

    override fun undeafen() {
        audioPlayer.undeafen()
        _uiState.update { state ->
            state.copy(
                isDeafened = false
            )
        }
    }

    override fun addClient(client: ClientData) {
        _uiState.update { state ->
            state.copy(
                clients = state.clients + client
            )
        }
    }

    override fun removeClient(client: ClientData) {
        _uiState.update { state ->
            state.copy(
                clients = state.clients.filter { it.id != client.id }
            )
        }
    }

    override fun removeClient(clientID: Int) {
        _uiState.update { state ->
            state.copy(
                clients = state.clients.filter { it.id != clientID }
            )
        }
    }

    override fun updateClient(client: ClientData) {
        _uiState.update { state ->
            state.copy(
                clients = state.clients.map {
                    if (it.id == client.id) {
                        client
                    } else {
                        it
                    }
                }
            )
        }
    }

    override fun changeClient(clientID: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.update { state ->
                    state.copy(
                        clients = state.clients.map {
                            if (it.id == clientID) {
                                clientSockets.getClientData(clientID)
                            } else {
                                it
                            }
                        }
                    )
                }
            }
        }
    }

    override fun addChannel(channel: ChannelData) {
        _uiState.update { state ->
            state.copy(
                channels = state.channels + channel
            )
        }
    }

    override fun addChannel(channelID: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.update { state ->
                    state.copy(
                        channels = state.channels + clientSockets.getChannelData(channelID)
                    )
                }
            }
        }
    }

    override fun removeChannel(channelID: Int) {
        _uiState.update { state ->
            state.copy(
                channels = state.channels.filter { it.id != channelID }
            )
        }
    }

    override fun updateChannel(channel: ChannelData) {
        _uiState.update { state ->
            state.copy(
                channels = state.channels.map {
                    if (it.id == channel.id) {
                        channel
                    } else {
                        it
                    }
                }
            )
        }
    }

    override fun moveChannel(channelID: Int, channelOrder: Int, channelParentID: Int) {
        _uiState.update { state ->
            state.copy(
                channels = state.channels.map {
                    if (it.id == channelID) {
                        it.copy(order = channelOrder, parent = channelParentID)
                    } else {
                        it
                    }
                }
            )
        }
    }

    override fun updateChannel(channelID: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.update { state ->
                    state.copy(
                        channels = state.channels.map {
                            if (it.id == channelID) {
                                clientSockets.getChannelData(channelID)
                            } else {
                                it
                            }
                        }
                    )
                }
            }
        }
    }

    override fun moveClient(clientID: Int, channelID: Int) {
        _uiState.update { state ->
            state.copy(
                clients = state.clients.map {
                    if (it.id == clientID) {
                        it.copy(channelId = channelID)
                    } else {
                        it
                    }
                }
            )
        }
    }



    fun updateClientList() {
        Log.d("AppViewModel", "updateClientList")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val clientList = clientSockets.clientList
                try {
                    val clientData = clientList.map { clientSockets.getClientData(it.id) }
                    _uiState.update { state ->
                        state.copy(clients = clientData)
                    }
                } catch (e: Exception) {
                    Log.w(
                        "AppViewModel",
                        "Failed to get client data: ${e.message},use base info instead"
                    )
                    _uiState.update { state ->
                        state.copy(clients = clientList.map { it.toData() })
                    }
                }
            }
        }
    }

    fun updateChannelList() {
        Log.d("AppViewModel", "updateChannelList")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val channelList = clientSockets.channelList.toList()
                _uiState.update { state ->
                    state.copy(
                        channels = channelList.map { it.toData() }
                    )
                }
            }
        }
    }
}