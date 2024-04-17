package site.sayaz.ts3client.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.manevolent.ts3j.api.Permission
import com.github.manevolent.ts3j.event.ChannelSubscribedEvent
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
import site.sayaz.ts3client.client.ClientSocket
import site.sayaz.ts3client.client.IdentityDataDao
import site.sayaz.ts3client.ui.channel.ChannelData
import site.sayaz.ts3client.ui.server.LoginData
import site.sayaz.ts3client.ui.server.LoginDataDao
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val loginDataDao: LoginDataDao,
    val identityDataDao: IdentityDataDao
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()
    private lateinit var clientSockets : ClientSocket

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
        val ts3Listener = object : TS3Listener{
            override fun onChannelSubscribed(e: ChannelSubscribedEvent?) {
                super.onChannelSubscribed(e)

            }
        }
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    socket.connect(ts3Listener)
                    // Connection successful
                    clientSockets = socket
                    delay(2000)
                    // uiState update
                    Log.d("AppViewModel", "connectServer: getting channel list")

//                    val channelList = socket.client.listChannels()
//                    val channelData = ChannelData(
//                        channelList.map { it.name }
//                    )
//                    //Log.d(TAG, "${socket.client.getChannelList().map { it.name }}")
//                    _uiState.update {
//                        it.copy(channels = it.channels + channelData)
//                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun disconnectAll() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Close all clients
                if (this@AppViewModel::clientSockets.isInitialized){
                    clientSockets.client.disconnect()
                }
            }
            _uiState.update { it.copy(
                channels = emptyList()
            ) }
        }
    }




}