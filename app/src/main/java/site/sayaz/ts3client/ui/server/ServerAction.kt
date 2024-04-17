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
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.navigation.NavController
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.navigation.MainRoute
import site.sayaz.ts3client.ui.util.InfoDialog


@Composable
fun ServerAction(navController: NavController) {
    IconButton(onClick = { navController.navigate(MainRoute.ADD_SERVER.name) }) {
        Icon(Icons.Filled.Add, contentDescription = "add Server")
    }
}


