package site.sayaz.ts3client.ui.channel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import site.sayaz.ts3client.ui.AppViewModel

@Composable
fun ChannelLayout(appViewModel: AppViewModel = viewModel()) {
    val appState by appViewModel.uiState.collectAsState()
    val channels = appState.channels
    val clients = appState.clients
    LazyColumn {
        items(channels) { channel ->
            ChannelItem(
                channel,
                clients.filter { it.channelId == channel.id },
                appViewModel::switchChannel
            )
        }
        //TODO 空白页面
    }
}