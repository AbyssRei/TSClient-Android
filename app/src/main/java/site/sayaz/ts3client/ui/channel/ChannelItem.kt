package site.sayaz.ts3client.ui.channel

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.manevolent.ts3j.api.Channel
import com.github.manevolent.ts3j.api.Client
import site.sayaz.ts3client.ui.util.FoldList

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChannelItem(channel: Channel,client: List<Client>, switchChannel: (Int) -> Unit) {
    val expandedState = remember {
        mutableStateOf(true)
    }
    Box(modifier = Modifier.clickable {
        switchChannel(channel.id)
    })
    {
        FoldList(
            expandedState = expandedState,
            title = {
                IconButton(onClick = {expandedState.value = !expandedState.value}) {
                    if (expandedState.value)
                        Icon(Icons.Default.ExpandMore,"Collapse",Modifier.size(16.dp))
                    else
                        Icon(Icons.Default.ExpandLess,"Expand",Modifier.size(16.dp))
                }
                Text(text = channel.name +""+ channel.parentChannelId)
                    },
            listItems = {
                client.forEach(){
                    Text(text = it.nickname)
                }
            }
        )
    }

}
