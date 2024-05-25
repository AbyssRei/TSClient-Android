package site.sayaz.ts3client.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.BedtimeOff
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import org.bouncycastle.math.raw.Mod
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.AppViewModel

@Composable
fun SettingsLayout(appViewModel: AppViewModel) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top
    ) {
        val appState by appViewModel.uiState.collectAsState()
        Icon(
            painterResource(R.drawable.baseline_headset_mic_24),
            "App Icon",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.size(32.dp))
        HorizontalDivider()

        //阻止应用睡眠
        SettingsListItem(icon = {
            Icon(Icons.Outlined.BedtimeOff, "Prevent Sleep", tint = MaterialTheme.colorScheme.primary)
        }, title = stringResource(R.string.prevent_sleep), onClick = {}) {
            Switch(checked = appState.settingsData.preventSleepDuringConnection,
                onCheckedChange = { checked ->
                appViewModel.preventSleepDuringConnection = checked
            })
        }




        HorizontalDivider()
        SettingsListItem(icon = {
            Icon(Icons.Outlined.Language, "Language", tint = MaterialTheme.colorScheme.primary)
        },
            title = stringResource(
                id =
                R.string.language
            ),
            onClick = { /*TODO*/ }) {

        }

        SettingsListItem(icon = {
            Icon(
                Icons.Outlined.ColorLens, "Appearance", tint = MaterialTheme.colorScheme.primary
            )
        }, title = stringResource(R.string.appearance), onClick = {

        }) {

        }

        HorizontalDivider()
        SettingsListItem(icon = {
            Icon(
                Icons.Outlined.Info,
                "info",
                tint = MaterialTheme.colorScheme.primary
            )
        },
            title = stringResource(id = R.string.about),
            onClick = {

            }) {}

    }




}

@Preview(
    backgroundColor = 0xFFFFFFFF, showBackground = true, widthDp = 300, heightDp = 600
)
@Composable
fun PreviewSettingsLayout() {
    SettingsLayout(viewModel())
}