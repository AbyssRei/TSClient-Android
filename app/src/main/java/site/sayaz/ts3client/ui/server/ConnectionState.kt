package site.sayaz.ts3client.ui.server

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 在服务器页显示连接状态
 */
@Parcelize
data class ServerConnectionState(
    val serverId: Int,
    val connectionState: ConnectionState
):Parcelable
enum class ConnectionState {
    NOT_CONNECTED,
    CONNECTING,
    CONNECTED,
    ERROR
}