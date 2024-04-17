package site.sayaz.ts3client.ui.channel

data class ChannelData (
    val channelID : Int,
    val channelName : String,
    val memberClients : List<String>,
)