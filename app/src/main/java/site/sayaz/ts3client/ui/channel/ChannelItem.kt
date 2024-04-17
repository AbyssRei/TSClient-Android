package site.sayaz.ts3client.ui.channel

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChannelItem(channel: ChannelData, switchChannel: (Int) -> Unit) {

    Text(text = channel.channelName,
        modifier = Modifier.clickable {
                switchChannel(channel.channelID)
        }
    )
}
