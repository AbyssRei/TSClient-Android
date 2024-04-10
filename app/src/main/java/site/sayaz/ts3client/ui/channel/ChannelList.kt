package site.sayaz.ts3client.ui.channel

import androidx.compose.runtime.Composable
import com.github.manevolent.ts3j.api.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import site.sayaz.ts3client.client.ClientSocket
import site.sayaz.ts3client.ui.AppViewModel


@Composable
fun ChannelList(channelList: List<String>){
    channelList.forEach {
        ChannelItem(it)
    }
}

