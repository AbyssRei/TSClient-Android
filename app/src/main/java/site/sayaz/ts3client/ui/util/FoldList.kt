package site.sayaz.ts3client.ui.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * A list of items that can be expanded to show more content.
 * @param title: The title of the list
 * @param items: The items to be displayed in the list
 * @param modifier: The modifier for the list
 *
 */

@ExperimentalAnimationApi
@Composable
fun FoldList(
    expandedState: MutableState<Boolean>,
    title: @Composable () -> Unit,
    listItems: @Composable ColumnScope.() -> Unit
) {
    Column {
        Row { title() }
        ExpandableContent(
            content = {
                Column {
                    listItems()
                }
            },
            expandedState = expandedState
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun ExpandableContent(
    content: @Composable () -> Unit,
    expandedState: MutableState<Boolean>
) {
        AnimatedVisibility(
            visible = expandedState.value,
            enter = expandVertically(animationSpec = tween(300)),
            exit = shrinkVertically(animationSpec = tween(300))
        ) {
            content()
        }
}

/**
 * an example
 */
@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun FoldListPreview() {
    val expandedState = remember { mutableStateOf(false) }
    FoldList(
        expandedState = expandedState,
        title = {
            IconButton(onClick = { expandedState.value = !expandedState.value }) {
                Icon(Icons.Filled.ExpandMore, contentDescription = "",Modifier.size(24.dp))
            }
            Text(text = "Title",Modifier.size(24.dp).fillMaxWidth())
        },
        listItems = {
            Text(text = "Item 1")
            Text(text = "Item 2")
        }

    )
}