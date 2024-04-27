package site.sayaz.ts3client.ui.channel

import android.icu.text.UnicodeMatcher
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.manevolent.ts3j.api.Channel
import com.github.manevolent.ts3j.api.Client
import site.sayaz.ts3client.ui.util.DotDivider
import site.sayaz.ts3client.ui.util.DottedLineDivider
import site.sayaz.ts3client.ui.util.DoubleDottedLineDivider
import site.sayaz.ts3client.ui.util.FoldList

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChannelItem(channel: Channel, client: List<Client>, switchChannel: (Int) -> Unit) {
    val expandedState = remember {
        mutableStateOf(true)
    }
    Box(modifier = Modifier
        .clickable {
            switchChannel(channel.id)
        }) {
        val regex = Regex("\\[(.*?)spacer(.*?)](.*?)")
        val matchResult = regex.matchEntire(channel.name)
        val alignment = matchResult?.groups?.get(1)?.value
        val channelName = matchResult?.groups?.get(2)?.value
        var text = matchResult?.groups?.get(3)?.value
        val channelModifier = Modifier.height(32.dp).fillMaxWidth()
        if (matchResult != null) {
            // Spacer
            val textAlign = when (alignment) {
                "l" -> TextAlign.Start
                "c" -> TextAlign.Center
                "r" -> TextAlign.End
                else -> TextAlign.Start
            }
            if (alignment!!.startsWith("*")) text = text!!.repeat(100)
            if (text!!.toString() == "---") text = "-".repeat(100)
            FoldList(expandedState = expandedState, title = {
                when (text.toString()) {
                    "..." -> DotDivider(
                        color = Color.Black,
                        modifier = channelModifier
                    )

                    "-.-" -> DottedLineDivider(
                        color = Color.Black,
                        modifier = channelModifier
                    )

                    "___" -> HorizontalDivider(
                        color = Color.Black,
                        modifier = channelModifier
                    )

                    "-.." -> DoubleDottedLineDivider(
                        color = Color.Black,
                        modifier = channelModifier
                    )

                    else -> {
                        Text(
                            text = text.toString(),
                            textAlign = textAlign,
                            modifier = channelModifier,
                            maxLines = 1
                        )
                    }
                }
            }, listItems = {
                client.forEach() {
                    Text(text = it.nickname)
                }
            })
        } else {
            // Normal
            FoldList(expandedState = expandedState, title = {
                Text(text = channel.name, maxLines = 1, modifier = Modifier.fillMaxWidth())
                IconButton(onClick = { expandedState.value = !expandedState.value }) {
                    if (expandedState.value) Icon(
                        Icons.Default.ExpandMore,
                        "Collapse",
                        Modifier.size(16.dp)
                    )
                    else Icon(Icons.Default.ExpandLess, "Expand", Modifier.size(16.dp))
                }

            }, listItems = {
                client.forEach() {
                    Text(text = it.nickname)
                }
            })
        }
    }

}
