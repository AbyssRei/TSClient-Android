package site.sayaz.ts3client.util

import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket
import com.github.manevolent.ts3j.api.Channel

suspend fun LocalTeamspeakClientSocket.getChannelList(): ArrayList<Channel> {
    val channelList = ArrayList<Channel>()
    this.listChannels().forEach{
         channelList.add(this.getChannelInfo(it.id))
    }
    return channelList
}