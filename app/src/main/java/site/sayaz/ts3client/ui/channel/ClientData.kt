package site.sayaz.ts3client.ui.channel

import android.os.Parcelable
import com.github.manevolent.ts3j.api.Client
import com.github.manevolent.ts3j.api.ClientInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClientData(
    val id : Int,
    val channelId : Int,
    val nickname : String,
    val state : ClientState = ClientState.TALKING
):Parcelable

fun Client.toData() : ClientData{
    if (isAway) return ClientData(id, channelId, nickname, ClientState.AWAY)
    if(isOutputMuted) return ClientData(id, channelId, nickname, ClientState.DEAFENED)
    if(isInputMuted) return ClientData(id, channelId, nickname, ClientState.MUTED)
    if (isTalking) return ClientData(id, channelId, nickname, ClientState.TALKING)
    return ClientData(id, channelId, nickname, ClientState.SILENT)
}

enum class ClientState {
    TALKING,
    SILENT,
    MUTED,
    DEAFENED,
    AWAY
}