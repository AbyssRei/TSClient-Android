package site.sayaz.ts3client.ui.server


import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import site.sayaz.ts3client.main.MainActivity
import site.sayaz.ts3client.ui.AppViewModel


@Composable
fun ServerLayout(appViewModel : AppViewModel = viewModel()) {
    val appState by appViewModel.uiState.collectAsState()
    val scrollState = rememberLazyListState()
    Box(Modifier.fillMaxSize()) {
        LazyColumn(state = scrollState) {
            items(appState.servers) { server ->
                ServerItem(loginData = server,appViewModel = appViewModel)
            }
        }
    }
}




