package site.sayaz.ts3client.ui.channel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChannelItem(channel: ChannelData, switchChannel: (Int) -> Unit) {
    Box(modifier = Modifier.clickable {
        switchChannel(channel.channelID)
    })
    {
        Text(text = channel.channelName)
    }

}
