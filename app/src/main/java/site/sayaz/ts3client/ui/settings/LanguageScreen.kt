package site.sayaz.ts3client.ui.settings

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import site.sayaz.ts3client.R
import site.sayaz.ts3client.main.MainActivity
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.util.toast


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(mainNavController: NavHostController, appViewModel: AppViewModel) {
    val context = LocalContext.current
    val appState by appViewModel.uiState.collectAsState()
    val defaultFollowSystemString = stringResource(R.string.default_value)
    val radioOptions = listOf(defaultFollowSystemString, "English", "简体中文")
    val selectedLanguage = remember {
        mutableStateOf(
            when (appState.settingsData.language) {
                "en" -> "English"
                "zh" -> "简体中文"
                else -> defaultFollowSystemString
            }
        )
    }
    val restartString = stringResource(id = R.string.restart_app_to_apply)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.language)) },
                navigationIcon = {
                    IconButton(onClick = { mainNavController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Close"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        appViewModel.changeLanguage(
                            when (selectedLanguage.value) {
                                "English" -> "en"
                                "简体中文" -> "zh"
                                else -> "def"

                            }
                        )
                        toast(context, restartString)
                        mainNavController.popBackStack()
                    }) {
                        Text(text = stringResource(id = R.string.apply))
                    }
                }
            )
        },

        ) {
        Column(modifier = Modifier.padding(it)) {
            radioOptions.forEach { option ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (selectedLanguage.value == option),
                            onClick = { selectedLanguage.value = option },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedLanguage.value == option),
                        onClick = { selectedLanguage.value = option }
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}