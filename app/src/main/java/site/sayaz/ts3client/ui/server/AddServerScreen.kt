package site.sayaz.ts3client.ui.server

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServerScreen(navController: NavController,appViewModel: AppViewModel) {
    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = stringResource(id = R.string.add_server)) },
            navigationIcon = { IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            } }
        ) }
    ) {
        AddServerContent(it,navController,appViewModel)
    }
}

@Composable
fun AddServerContent(
    paddingValues: PaddingValues,
    navController: NavController,
    appViewModel: AppViewModel
) {
    val labels = listOf(R.string.hostname, R.string.password, R.string.nickname)
    val textFields = remember { mutableStateListOf<TextFieldValue>() }
    for (i in labels.indices) textFields.add(TextFieldValue())
    Box (modifier = Modifier.padding(paddingValues)){
        Column(modifier = Modifier.padding(16.dp)) {

            labels.forEachIndexed { index, label ->
                OutlinedTextField(
                    value = textFields[index],
                    onValueChange = { textFields[index] = it },
                    label = { Text(stringResource(id = label)) })
                Spacer(modifier = Modifier.padding(8.dp))
            }

            Row {
                TextButton(onClick = {
                    navController.popBackStack()
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
                    navController.popBackStack()
                }) {
                    Text(stringResource(id = R.string.add))
                }
            }
        }
    }
}