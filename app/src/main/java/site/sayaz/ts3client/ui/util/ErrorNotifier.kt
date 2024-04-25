package site.sayaz.ts3client.ui.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.AppViewModel


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ErrorNotifier(appViewModel: AppViewModel, snackbarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    val appState = appViewModel.uiState.collectAsState()
    val errorMessage = appState.value.errorMessage
    Log.d("ErrorNotifier", errorMessage)

    val insufficientClientPermissions =
        stringResource(id = R.string.insufficient_client_permissions)
    val connectionTimeOut =
        stringResource(id = R.string.connection_timeout)
    val channelMaxClientReached =
        stringResource(id = R.string.channel_max_client_reached)
    val tooManyClonesAlreadyConnected =
        stringResource(id = R.string.too_many_clones_already_connected)

    scope.launch {
        when (errorMessage) {
            "insufficient client permissions" -> {
                snackbarHostState.showSnackbar(
                    message = insufficientClientPermissions,
                )
            }
            "timeout waiting for CONNECTED state" -> {
                snackbarHostState.showSnackbar(
                    message = connectionTimeOut,
                )
            }
            "channel maxclient reached" -> {
                snackbarHostState.showSnackbar(
                    message = channelMaxClientReached,
                )
            }
            "too many clones already connected" -> {
                snackbarHostState.showSnackbar(
                    message = tooManyClonesAlreadyConnected
                )
            }
        }
    }

}
