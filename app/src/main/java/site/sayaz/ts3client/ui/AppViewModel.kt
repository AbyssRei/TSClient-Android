package site.sayaz.ts3client.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.manevolent.ts3j.event.TS3Listener
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
import site.sayaz.ts3client.audio.AudioRecorder
import site.sayaz.ts3client.client.ClientSocket
import site.sayaz.ts3client.client.IdentityDataDao
import site.sayaz.ts3client.ui.channel.ChannelData
import site.sayaz.ts3client.client.ServerData
import site.sayaz.ts3client.client.ServerDataDao
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val identityDataDao: IdentityDataDao,
    val serverDataDao: ServerDataDao,
    application : Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()
    private lateinit var clientSockets: ClientSocket

    init {
        Log.d("AppViewModel", "init viewmodel")
        viewModelScope.launch {
            val serversFromDb = serverDataDao.getAll()
            if (serversFromDb.isNotEmpty()) {
                _uiState.value = AppState(emptyList(), serversFromDb, "")
            }
        }
    }


    fun insertServer(server: ServerData) {
        _uiState.update {
            viewModelScope.launch { serverDataDao.insert(server) }
            it.copy(servers = it.servers + server)
        }
    }

    /**
     * Connect to the server and get the channel list
     * @param server: ServerData
     * @throws Exception
     */
    fun connectServer(server: ServerData) {
        val audioRecorder = AudioRecorder(getApplication(), AudioInput())
        val socket = ClientSocket(server, identityDataDao, audioRecorder)
        val ts3Listener = object : TS3Listener {}
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    socket.connect(ts3Listener)
                    // Connection successful
                    clientSockets = socket
                    onConnectSuccess(server)
                    // uiState update
                    // TODO 连接状态

                }
            } catch (e: Exception) {
                showErrorMessage(e.message)
                e.printStackTrace()
            }
        }
    }

    private fun onConnectSuccess(server: ServerData) {
        viewModelScope.launch {try{
            // get channel list
            withContext(Dispatchers.IO) {
                clientSockets.channelList.forEach { channel ->
                    _uiState.update {
                        it.copy(channels = it.channels + ChannelData(
                            channelID = channel.id,
                            channelName = channel.name,
                            members = emptyList()
                        ))
                    }
                }
            }
        }catch (e: Exception){
            showErrorMessage(e.message)
            e.printStackTrace()
        } }
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
                if (this@AppViewModel::clientSockets.isInitialized) {
                    clientSockets.disconnect()
                }
            }
            _uiState.update {
                it.copy(
                    channels = emptyList()
                )
            }
        }
    }
    private fun showErrorMessage(message: String?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    errorMessage = message?:""
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
}