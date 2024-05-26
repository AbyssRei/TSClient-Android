package site.sayaz.ts3client.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.BedtimeOff
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.navigation.MainRoute
import site.sayaz.ts3client.ui.util.toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(mainNavController: NavHostController, appViewModel: AppViewModel) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.about)) },
                navigationIcon = {
                    IconButton(onClick = { mainNavController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            val appState by appViewModel.uiState.collectAsState()
            Text("(<ゝω・) ☆", style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
                lineHeight = 60.sp,
                letterSpacing = 1.sp
            ),)
            Spacer(modifier = Modifier.size(32.dp))
            HorizontalDivider()
            ListItem(headlineContent = {
                Text(text = stringResource(id = R.string.version))
            },
                supportingContent = {
                    Text(text = "Stable 1.0")
                }
            )
            ListItem(headlineContent = {
                Text(text = stringResource(id = R.string.open_source_licences))
            },
                modifier = Modifier.clickable {
                    mainNavController.navigate(MainRoute.ABOUT_OPENSOURCE.name)
                }
            )
        }
    }
}
