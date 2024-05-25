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
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.channel.ChannelData
import site.sayaz.ts3client.ui.channel.ChannelStateInterface
import site.sayaz.ts3client.ui.channel.ClientData

class Listener(private val state : ChannelStateInterface) :
    TS3Listener {
        //TODO to be deleted
    private val debug = true



    override fun onTextMessage(e: TextMessageEvent?) {
        if (debug) {
            Log.d("Listener", "Text message")
        }
        super.onTextMessage(e)

    }

    override fun onClientJoin(e: ClientJoinEvent?) {
        if (debug) {
            Log.d("Listener", "Client join")
        }
        super.onClientJoin(e)
        e?:return
        state.addClient(ClientData(e.clientId, e.clientChannelGroupId,e.clientNickname))
    }

    override fun onClientLeave(e: ClientLeaveEvent?) {
        if (debug) {
            Log.d("Listener", "Client leave")
        }
        super.onClientLeave(e)
        e?:return
        state.removeClient(e.clientId)
    }

    override fun onServerEdit(e: ServerEditedEvent?) {
        if (debug) {
            Log.d("Listener", "Server edit")
        }
        //TODO
        super.onServerEdit(e)
    }

    override fun onChannelEdit(e: ChannelEditedEvent?) {
        if (debug) {
            Log.d("Listener", "Channel edit")
        }
        super.onChannelEdit(e)
        e?:return
        state.updateChannel(e.channelId)
    }

    override fun onChannelDescriptionChanged(e: ChannelDescriptionEditedEvent?) {
        if (debug) {
            Log.d("Listener", "Channel description changed")
        }
        super.onChannelDescriptionChanged(e)
        //TODO
    }

    override fun onClientMoved(e: ClientMovedEvent?) {
        if (debug) {
            Log.d("Listener", "Client moved")
        }
        super.onClientMoved(e)
        e?:return
        state.moveClient(e.clientId, e.targetChannelId)

    }

    override fun onChannelCreate(e: ChannelCreateEvent?) {
        if (debug) {
            Log.d("Listener", "Channel create")
        }
        super.onChannelCreate(e)
        e?:return
        state.addChannel(e.channelId)
    }

    override fun onChannelDeleted(e: ChannelDeletedEvent?) {
        if (debug) {
            Log.d("Listener", "Channel deleted")
        }
        super.onChannelDeleted(e)
        e?:return
        state.removeChannel(e.channelId)
    }

    override fun onChannelMoved(e: ChannelMovedEvent?) {
        if (debug) {
            Log.d("Listener", "Channel moved")
        }
        super.onChannelMoved(e)
        e?:return
        state.moveChannel(e.channelId,e.channelOrder,e.channelParentId)
    }

    override fun onChannelPasswordChanged(e: ChannelPasswordChangedEvent?) {
        if (debug) {
            Log.d("Listener", "Channel password changed")
        }
        super.onChannelPasswordChanged(e)
        //TODO() updateChannel()
    }

    override fun onChannelList(e: ChannelListEvent?) {
        if (debug) {
            Log.d("Listener", "Channel list")
        }
        super.onChannelList(e)
    }

    override fun onPrivilegeKeyUsed(e: PrivilegeKeyUsedEvent?) {
        if (debug) {
            Log.d("Listener", "Privilege key used")
        }
        super.onPrivilegeKeyUsed(e)
    }

    override fun onChannelGroupList(e: ChannelGroupListEvent?) {
        if (debug) {
            Log.d("Listener", "Channel group list")
        }
        super.onChannelGroupList(e)
    }

    override fun onServerGroupList(e: ServerGroupListEvent?) {
        if (debug) {
            Log.d("Listener", "Server group list")
        }
        super.onServerGroupList(e)
    }

    override fun onClientNeededPermissions(e: ClientNeededPermissionsEvent?) {
        if (debug) {
            Log.d("Listener", "Client needed permissions")
        }
        super.onClientNeededPermissions(e)
    }

    override fun onClientChannelGroupChanged(e: ClientChannelGroupChangedEvent?) {
        if (debug) {
            Log.d("Listener", "Client channel group changed")
        }
        super.onClientChannelGroupChanged(e)
    }

    override fun onClientChanged(e: ClientUpdatedEvent?) {
        if (debug) {
            Log.d("Listener", "Client changed")
        }
        super.onClientChanged(e)
        e?:return
        state.changeClient(e.clientId)

    }

    override fun onDisconnected(e: DisconnectedEvent?) {
        if (debug) {
            Log.d("Listener", "Disconnected")
        }
        super.onDisconnected(e)
    }

    override fun onChannelSubscribed(e: ChannelSubscribedEvent?) {
        if (debug) {
            Log.d("Listener", "Channel subscribed")
        }
        super.onChannelSubscribed(e)

    }

    override fun onChannelUnsubscribed(e: ChannelUnsubscribedEvent?) {
        if (debug) {
            Log.d("Listener", "Channel unsubscribed")
        }
        super.onChannelUnsubscribed(e)
    }

    override fun onServerGroupClientAdded(e: ServerGroupClientAddedEvent?) {
        if (debug) {
            Log.d("Listener", "Server group client added")
        }
        super.onServerGroupClientAdded(e)
    }

    override fun onServerGroupClientDeleted(e: ServerGroupClientDeletedEvent?) {
        if (debug) {
            Log.d("Listener", "Server group client deleted")
        }
        super.onServerGroupClientDeleted(e)
    }

    override fun onClientPoke(e: ClientPokeEvent?) {
        if (debug) {
            Log.d("Listener", "Client poke")
        }
        super.onClientPoke(e)//TODO
    }

    override fun onClientComposing(e: ClientChatComposingEvent?) {
        if (debug) {
            Log.d("Listener", "Client composing")
        }
        super.onClientComposing(e)

    }

    override fun onPermissionList(e: PermissionListEvent?) {
        if (debug) {
            Log.d("Listener", "Permission list")
        }
        super.onPermissionList(e)
    }

    override fun onConnected(e: ConnectedEvent?) {
        if (debug) {
            Log.d("Listener", "Connected")
        }
        super.onConnected(e)
    }

    override fun onClientChatClosed(e: ClientChatClosedEvent?) {
        if (debug) {
            Log.d("Listener", "Client chat closed")
        }
        super.onClientChatClosed(e)
    }

    override fun onClientPermHints(e: ClientPermHintsEvent?) {
        if (debug) {
            Log.d("Listener", "Client perm hints")
        }
        super.onClientPermHints(e)
    }

    override fun onChannelPermHints(e: ChannelPermHintsEvent?) {
        if (debug) {
            Log.d("Listener", "Channel perm hints")
        }
        super.onChannelPermHints(e)
    }

    override fun onUnknownEvent(e: UnknownTeamspeakEvent?) {
        super.onUnknownEvent(e)
        Log.e("Listener", "Unknown event")
    }
}