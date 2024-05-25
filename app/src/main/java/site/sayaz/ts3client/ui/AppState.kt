package site.sayaz.ts3client.ui

import android.os.Parcelable
import com.github.manevolent.ts3j.api.Channel
import com.github.manevolent.ts3j.api.Client
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import site.sayaz.ts3client.client.ServerData
import site.sayaz.ts3client.settings.SettingsData
import site.sayaz.ts3client.ui.channel.ChannelData
import site.sayaz.ts3client.ui.channel.ClientData
import site.sayaz.ts3client.ui.server.ServerConnectionState

@Parcelize
data class AppState (
    val servers: List<ServerData> = emptyList(),
    val channels: List<ChannelData> = emptyList(),
    val clients: List<ClientData> = emptyList(),
    // 服务器列表连接状态
    val serverConnectionStates: List<ServerConnectionState> = emptyList(),
    // 有服务器连接
    val isInConnect: Boolean = false,
    // audio
    val isMuted: Boolean = false,
    val isDeafened: Boolean = false,
    // settings
    val settingsData: SettingsData = SettingsData(),

    val errorMessage: String = "",
) : Parcelable