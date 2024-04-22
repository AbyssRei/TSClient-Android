package site.sayaz.ts3client.ui

import site.sayaz.ts3client.ui.channel.ChannelData
import site.sayaz.ts3client.client.LoginData

/**
 * AppState
 * @constructor Create empty App state
 * @param channels: List of ChannelData
 * @param servers: List of LoginData
 */
data class AppState(
    val channels : List<ChannelData> = emptyList(),
    val servers: List<LoginData> = emptyList(),
    val errorMessage : String = "",
)