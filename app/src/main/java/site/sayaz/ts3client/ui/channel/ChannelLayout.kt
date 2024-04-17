package site.sayaz.ts3client.ui.channel

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import site.sayaz.ts3client.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelLayout(appViewModel : AppViewModel = viewModel()){
    var i by remember { mutableIntStateOf(0) }
    val appState by appViewModel.uiState.collectAsState()
    val channels = appState.channels
    Column {
        PrimaryTabRow(selectedTabIndex = i) {
            channels.forEachIndexed { index, channel ->

            }
        }

        if (channels.isNotEmpty()) {
            ChannelList(channels[i].channelName)
        }else{

        }
    }
}