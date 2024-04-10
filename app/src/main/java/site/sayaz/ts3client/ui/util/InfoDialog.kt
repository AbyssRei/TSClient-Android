package site.sayaz.ts3client.ui.util

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun InfoDialog(
    openDialog: MutableState<Boolean>,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    if (openDialog.value) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Card(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}


