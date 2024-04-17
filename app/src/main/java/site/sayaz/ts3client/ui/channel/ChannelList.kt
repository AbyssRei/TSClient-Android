package site.sayaz.ts3client.ui.channel

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ChannelList(channels: List<ChannelData>, switchChannel: (Int) -> Unit){
    channels.forEach {
        ChannelItem(it, switchChannel)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

