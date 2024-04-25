package site.sayaz.ts3client.ui

import site.sayaz.ts3client.ui.channel.ChannelData
import site.sayaz.ts3client.client.ServerData
import site.sayaz.ts3client.ui.server.ServerConnectionState

/**
 * AppState
 * @constructor Create empty App state
 * @param channels: List of ChannelData
 * @param servers: List of ServerData
 */
data class AppState(
    val channels : List<ChannelData> = emptyList(),
    val servers: List<ServerData> = emptyList(),
    val errorMessage : String = "",
    val serverConnectionStates: List<ServerConnectionState> = emptyList(),
    val isInConnect : Boolean = false
)