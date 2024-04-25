package site.sayaz.ts3client.ui

import com.github.manevolent.ts3j.api.Channel
import com.github.manevolent.ts3j.api.Client
import site.sayaz.ts3client.client.ServerData
import site.sayaz.ts3client.ui.server.ServerConnectionState

/**
 * AppState
 * @constructor Create empty App state
 * @param channels: List of ChannelData
 * @param servers: List of ServerData
 */
data class AppState(
    val servers: List<ServerData> = emptyList(),
    val channels : List<Channel> = emptyList(),
    val clients : List<Client> = emptyList(),

    val serverConnectionStates: List<ServerConnectionState> = emptyList(),
    val isInConnect : Boolean = false,

    val errorMessage : String = "",
)