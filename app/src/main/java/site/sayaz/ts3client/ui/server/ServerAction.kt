package site.sayaz.ts3client.ui.server

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import site.sayaz.ts3client.ui.navigation.MainRoute


@Composable
fun ServerAction(navController: NavController) {
    IconButton(onClick = { navController.navigate(MainRoute.ADD_SERVER.name) }) {
        Icon(Icons.Filled.Add, contentDescription = "add Server")
    }
    //TODO 多选
}


