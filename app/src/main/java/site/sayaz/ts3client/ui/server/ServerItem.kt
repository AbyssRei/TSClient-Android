package site.sayaz.ts3client.ui.server

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import site.sayaz.ts3client.client.ServerData
import site.sayaz.ts3client.ui.AppViewModel


@Composable
fun ServerItem(serverData: ServerData, appViewModel: AppViewModel) {
    val appState by appViewModel.uiState.collectAsState()
    ListItem(
        headlineContent = { Text(serverData.hostname) },
        supportingContent = { Text(serverData.nickname) },
        trailingContent = {
            IconButton(onClick = { if (!appState.isInConnect) appViewModel.connectServer(serverData) }) {
                when (appState.serverConnectionStates.find { it.serverId == serverData.id }?.connectionState) {

                    ConnectionState.CONNECTED -> Icon(
                        Icons.Default.Done,
                        contentDescription = "serverData Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )

                    ConnectionState.CONNECTING -> CircularProgressIndicator(Modifier.size(24.dp))

                    ConnectionState.ERROR -> Icon(
                        Icons.Default.Close,
                        contentDescription = "serverData Icon",
                        tint = if (appState.isInConnect) Color.Gray else MaterialTheme.colorScheme.error
                    )

                    ConnectionState.NOT_CONNECTED -> Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = "serverData Icon",
                        tint = if (appState.isInConnect) Color.Gray else MaterialTheme.colorScheme.secondary
                    )
                    null -> {}
                }
            }
        },
        leadingContent = {
            Icon(
                Icons.Outlined.Dns,
                contentDescription = "serverData Icon",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
}