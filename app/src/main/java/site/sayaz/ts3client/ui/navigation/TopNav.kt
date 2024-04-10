package site.sayaz.ts3client.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNav(title: String, actions: @Composable () -> Unit){
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(),
        actions = { actions() }
    )
}