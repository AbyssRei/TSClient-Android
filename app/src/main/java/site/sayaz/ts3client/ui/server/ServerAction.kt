package site.sayaz.ts3client.ui.server

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.util.InfoDialog

@Composable
fun ServerAction() {
    val openDialog = remember { mutableStateOf(false) }
    val onDismissRequest = { openDialog.value = false }
    InfoDialog(openDialog = openDialog, onDismissRequest) {
        AddServer(onDismissRequest)
    }

    IconButton(onClick = { openDialog.value = true }) {
        Icon(Icons.Filled.Add, contentDescription = "add Server")
    }

}

@Preview
@Composable
private fun AddServer(onDismissRequest: () -> Unit = { }, appViewModel : AppViewModel = viewModel()) {
    val labels = listOf(R.string.hostname, R.string.password, R.string.nickname)
    val textFields = remember { mutableStateListOf<TextFieldValue>() }
    for (i in labels.indices) textFields.add(TextFieldValue())
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            stringResource(id = R.string.add_server),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.padding(8.dp))

        labels.forEachIndexed { index, label ->
            OutlinedTextField(
                value = textFields[index],
                onValueChange = { textFields[index] = it },
                label = { Text(stringResource(id = label)) })
            Spacer(modifier = Modifier.padding(8.dp))
        }

        Row {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text(stringResource(id = R.string.cancel))
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = {
                appViewModel.insertServer(
                    LoginData(
                        0,
                        textFields[0].text,
                        textFields[1].text,
                        textFields[2].text
                    )
                )
                onDismissRequest()
            }) {
                Text(stringResource(id = R.string.add))
            }
        }
    }
}


