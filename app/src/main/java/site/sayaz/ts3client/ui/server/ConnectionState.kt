package site.sayaz.ts3client.ui.server

/**
 * 在服务器页显示连接状态
 */
data class ServerConnectionState(
    val serverId: Int,
    val connectionState: ConnectionState
)
enum class ConnectionState {
    NOT_CONNECTED,
    CONNECTING,
    CONNECTED,
    ERROR
}