package site.sayaz.ts3client.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import site.sayaz.ts3client.client.ClientSocket
import site.sayaz.ts3client.client.IdentityDataDao
import site.sayaz.ts3client.ui.channel.ChannelData
import site.sayaz.ts3client.ui.server.LoginData
import site.sayaz.ts3client.ui.server.LoginDataDao
import site.sayaz.ts3client.util.getChannelList
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val loginDataDao: LoginDataDao,
    val identityDataDao: IdentityDataDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()
    private lateinit var clientSockets : MutableMap<String, ClientSocket>

    init {
        Log.d("AppViewModel", "init! ")
        viewModelScope.launch {
            val serversFromDb = loginDataDao.getAll()
            if (serversFromDb.isNotEmpty()) {
                _uiState.value = AppState(emptyList(), serversFromDb)
            }
        }
    }


    fun insertServer(server: LoginData) {
        _uiState.update {
            viewModelScope.launch { loginDataDao.insert(server) }
            it.copy(servers = it.servers + server)
        }
    }

    fun connectServer(server: LoginData) {
        val socket = ClientSocket(loginData = server, identityDataDao = identityDataDao)

        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    socket.connect()

                    // Connection successful
                    clientSockets[server.hostname] = socket

                    // uiState update
                    val channelData = ChannelData(
                        server.hostname,
                        socket.client.getChannelList().map { it.name }
                    )
                    _uiState.update {
                        it.copy(channels = it.channels + channelData)
                    }

                }
            } catch (e: Exception) {
                Log.d("ServerViewModel", "connectServer: ${e.message}")
            }
        }
    }

    fun disconnectAll() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Close all clients
                if(clientSockets.isNotEmpty()){
                    clientSockets.forEach { (hostname, socket) ->
                        Log.d("AppViewModel", "disconnectAll: Disconnecting $hostname")
                        socket.client.disconnect("Bye~")
                        socket.client.close()
                    }
                }
            }
            _uiState.update { it.copy(
                channels = emptyList()
            ) }
        }
    }


}