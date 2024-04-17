package site.sayaz.ts3client.ui.channel

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ChannelItem(channel : ChannelData){
    Text(text = channel.channelName)

}