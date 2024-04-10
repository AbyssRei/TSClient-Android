package site.sayaz.ts3client.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.channel.ChannelLayout
import site.sayaz.ts3client.ui.navigation.BottomNav
import site.sayaz.ts3client.ui.navigation.NavItem
import site.sayaz.ts3client.ui.navigation.TopNav
import site.sayaz.ts3client.ui.server.ServerLayout
import site.sayaz.ts3client.ui.settings.SettingsLayout

@Composable
fun MainScreen(
    navController: NavHostController){
    val appViewModel : AppViewModel = viewModel()
    val navItems = NavItem.getItems()
    val topBarTitleID = remember { mutableIntStateOf(navItems[0].titleID) }
    val topBarActions = remember { mutableStateOf(navItems[0].actions) }
    Scaffold(
        topBar = { TopNav(title = stringResource(id = topBarTitleID.intValue), actions = topBarActions.value) },
        bottomBar = { BottomNav(navController = navController, title = topBarTitleID, actions = topBarActions) }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = navItems[0].route){
            composable(navItems[0].route){
                Box(modifier = Modifier.padding(paddingValues)){
                    ServerLayout(appViewModel)
                }
            }
            composable(navItems[1].route){
                Box(modifier = Modifier.padding(paddingValues)){
                    ChannelLayout(appViewModel)
                }
            }
            composable(navItems[2].route){
                Box(modifier = Modifier.padding(paddingValues)){
                    SettingsLayout()
                }
            }
        }
    }
}

