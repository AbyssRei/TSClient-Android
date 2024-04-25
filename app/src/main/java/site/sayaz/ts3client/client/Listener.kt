package site.sayaz.ts3client.client

import android.util.Log
import com.github.manevolent.ts3j.event.ChannelCreateEvent
import com.github.manevolent.ts3j.event.ChannelDeletedEvent
import com.github.manevolent.ts3j.event.ChannelDescriptionEditedEvent
import com.github.manevolent.ts3j.event.ChannelEditedEvent
import com.github.manevolent.ts3j.event.ChannelGroupListEvent
import com.github.manevolent.ts3j.event.ChannelListEvent
import com.github.manevolent.ts3j.event.ChannelMovedEvent
import com.github.manevolent.ts3j.event.ChannelPasswordChangedEvent
import com.github.manevolent.ts3j.event.ChannelPermHintsEvent
import com.github.manevolent.ts3j.event.ChannelSubscribedEvent
import com.github.manevolent.ts3j.event.ChannelUnsubscribedEvent
import com.github.manevolent.ts3j.event.ClientChannelGroupChangedEvent
import com.github.manevolent.ts3j.event.ClientChatClosedEvent
import com.github.manevolent.ts3j.event.ClientChatComposingEvent
import com.github.manevolent.ts3j.event.ClientJoinEvent
import com.github.manevolent.ts3j.event.ClientLeaveEvent
import com.github.manevolent.ts3j.event.ClientMovedEvent
import com.github.manevolent.ts3j.event.ClientNeededPermissionsEvent
import com.github.manevolent.ts3j.event.ClientPermHintsEvent
import com.github.manevolent.ts3j.event.ClientPokeEvent
import com.github.manevolent.ts3j.event.ClientUpdatedEvent
import com.github.manevolent.ts3j.event.ConnectedEvent
import com.github.manevolent.ts3j.event.DisconnectedEvent
import com.github.manevolent.ts3j.event.PermissionListEvent
import com.github.manevolent.ts3j.event.PrivilegeKeyUsedEvent
import com.github.manevolent.ts3j.event.ServerEditedEvent
import com.github.manevolent.ts3j.event.ServerGroupClientAddedEvent
import com.github.manevolent.ts3j.event.ServerGroupClientDeletedEvent
import com.github.manevolent.ts3j.event.ServerGroupListEvent
import com.github.manevolent.ts3j.event.TS3Listener
import com.github.manevolent.ts3j.event.TextMessageEvent
import com.github.manevolent.ts3j.event.UnknownTeamspeakEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class Listener(private val updateChannel: () -> Unit, private val updateClient: () -> Unit) :
    TS3Listener {



    override fun onTextMessage(e: TextMessageEvent?) {
        super.onTextMessage(e)
    }

    override fun onClientJoin(e: ClientJoinEvent?) {
        super.onClientJoin(e)
        updateClient()
    }

    override fun onClientLeave(e: ClientLeaveEvent?) {
        super.onClientLeave(e)
        updateClient()
    }

    override fun onServerEdit(e: ServerEditedEvent?) {
        super.onServerEdit(e)
    }

    override fun onChannelEdit(e: ChannelEditedEvent?) {
        super.onChannelEdit(e)
        updateChannel()
    }

    override fun onChannelDescriptionChanged(e: ChannelDescriptionEditedEvent?) {
        super.onChannelDescriptionChanged(e)
        updateChannel()
    }

    override fun onClientMoved(e: ClientMovedEvent?) {
        super.onClientMoved(e)
        updateClient()
    }

    override fun onChannelCreate(e: ChannelCreateEvent?) {
        super.onChannelCreate(e)
        updateChannel()
    }

    override fun onChannelDeleted(e: ChannelDeletedEvent?) {
        super.onChannelDeleted(e)
        updateChannel()
    }

    override fun onChannelMoved(e: ChannelMovedEvent?) {
        super.onChannelMoved(e)
        updateChannel()
    }

    override fun onChannelPasswordChanged(e: ChannelPasswordChangedEvent?) {
        super.onChannelPasswordChanged(e)
        updateChannel()
    }

    override fun onChannelList(e: ChannelListEvent?) {
        super.onChannelList(e)
    }

    override fun onPrivilegeKeyUsed(e: PrivilegeKeyUsedEvent?) {
        super.onPrivilegeKeyUsed(e)
    }

    override fun onChannelGroupList(e: ChannelGroupListEvent?) {
        super.onChannelGroupList(e)
    }

    override fun onServerGroupList(e: ServerGroupListEvent?) {
        super.onServerGroupList(e)
    }

    override fun onClientNeededPermissions(e: ClientNeededPermissionsEvent?) {
        super.onClientNeededPermissions(e)
    }

    override fun onClientChannelGroupChanged(e: ClientChannelGroupChangedEvent?) {
        super.onClientChannelGroupChanged(e)
        updateClient()
    }

    override fun onClientChanged(e: ClientUpdatedEvent?) {
        super.onClientChanged(e)
        updateClient()
    }

    override fun onDisconnected(e: DisconnectedEvent?) {
        super.onDisconnected(e)
    }

    override fun onChannelSubscribed(e: ChannelSubscribedEvent?) {
        super.onChannelSubscribed(e)
    }

    override fun onChannelUnsubscribed(e: ChannelUnsubscribedEvent?) {
        super.onChannelUnsubscribed(e)
    }

    override fun onServerGroupClientAdded(e: ServerGroupClientAddedEvent?) {
        super.onServerGroupClientAdded(e)
    }

    override fun onServerGroupClientDeleted(e: ServerGroupClientDeletedEvent?) {
        super.onServerGroupClientDeleted(e)
    }

    override fun onClientPoke(e: ClientPokeEvent?) {
        super.onClientPoke(e)
    }

    override fun onClientComposing(e: ClientChatComposingEvent?) {
        super.onClientComposing(e)

    }

    override fun onPermissionList(e: PermissionListEvent?) {
        super.onPermissionList(e)
    }

    override fun onConnected(e: ConnectedEvent?) {
        super.onConnected(e)
    }

    override fun onClientChatClosed(e: ClientChatClosedEvent?) {
        super.onClientChatClosed(e)
    }

    override fun onClientPermHints(e: ClientPermHintsEvent?) {
        super.onClientPermHints(e)
    }

    override fun onChannelPermHints(e: ChannelPermHintsEvent?) {
        super.onChannelPermHints(e)
    }

    override fun onUnknownEvent(e: UnknownTeamspeakEvent?) {
        super.onUnknownEvent(e)
        Log.e("Listener", "Unknown event")
    }
}