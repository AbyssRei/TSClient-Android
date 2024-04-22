package site.sayaz.ts3client.ui.server

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import site.sayaz.ts3client.client.LoginData


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ServerItem(loginData: LoginData, connectServer : (LoginData) -> Unit) {
    ListItem(
        headlineContent = { Text(loginData.hostname) },
        supportingContent = { Text(loginData.nickname) },
        trailingContent = {
            IconButton(onClick = { connectServer(loginData) }) {
                Icon(Icons.Default.PlayArrow, contentDescription = "enter")
            }
        },
        leadingContent = {
//            Icon(
//                Icons.Default.Info,
//                contentDescription = "loginData Icon",
//            )
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
}