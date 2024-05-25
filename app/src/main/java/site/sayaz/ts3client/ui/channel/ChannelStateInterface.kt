package site.sayaz.ts3client.ui.channel

interface ChannelStateInterface {
    fun addChannel(channel: ChannelData)
    fun addChannel(channelID: Int)
    fun removeChannel(channelID: Int)
    fun updateChannel(channel: ChannelData)
    fun updateChannel(channelID: Int)
    fun moveChannel(channelID: Int, channelOrder: Int, channelParentID: Int)
    fun addClient(client: ClientData)
    fun removeClient(client: ClientData)
    fun removeClient(clientID: Int)
    fun updateClient(client: ClientData)
    fun moveClient(clientID: Int, channelID: Int)
    fun changeClient(clientID: Int)

}