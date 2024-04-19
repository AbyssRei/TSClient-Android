package site.sayaz.ts3client.ui.server

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Visibility
import androidx.navigation.NavController
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServerScreen(navController: NavController, appViewModel: AppViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.add_server)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        AddServerContent(it, navController, appViewModel)
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

    var serverTextIsErr by remember { mutableStateOf(false) }
    fun validateServer(text: String) {
        val ipRegex =
            """^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$""".toRegex()
        val domainRegex = """^([a-z0-9]+(-[a-z0-9]+)*\.)+[a-z]{2,}$""".toRegex()
        serverTextIsErr = !(ipRegex.matches(text) or domainRegex.matches(text))
    }

    var nicknameTextIsErr by remember { mutableStateOf(false) }
    fun validateNickname(text: String) {
        nicknameTextIsErr = text.isEmpty() and text.isBlank()
    }

    var passwordHidden by rememberSaveable { mutableStateOf(true) }


    for (i in labels.indices) textFields.add(TextFieldValue())
    Box(modifier = Modifier.padding(paddingValues)) {
        Column(modifier = Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = textFields[0],
                onValueChange = {
                    textFields[0] = it
                    validateServer(it.text)
                },
                label = { Text(stringResource(id = R.string.hostname)) },
                maxLines = 1,
                isError = serverTextIsErr
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = textFields[1],
                onValueChange = { textFields[1] = it },
                label = { Text(stringResource(id = R.string.password)) },
                maxLines = 1,
                visualTransformation =
                if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        val visibilityIcon =
                            if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description = if (passwordHidden) "Show password" else "Hide password"
                        Icon(imageVector = visibilityIcon, contentDescription = description)
                    }
                },
                supportingText = {
                    Text(
                        text = stringResource(id = R.string.password_supporting_text)
                    )
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = textFields[2],
                onValueChange = {
                    textFields[2] = it
                    validateNickname(it.text)
                },
                label = { Text(stringResource(id = R.string.nickname)) },
                maxLines = 1,
                isError = nicknameTextIsErr
            )

            Row {

                TextButton(onClick = {
                    if (serverTextIsErr or nicknameTextIsErr) return@TextButton
                    appViewModel.insertServer(
                        LoginData(
                            0,
                            textFields[0].text,
                            textFields[1].text,
                            textFields[2].text
                        )
                    )
                    navController.popBackStack()
                }
                ) {
                    Text(stringResource(id = R.string.add))
                }
            }
        }
    }
}