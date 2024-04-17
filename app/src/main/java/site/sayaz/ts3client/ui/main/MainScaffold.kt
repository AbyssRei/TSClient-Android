package site.sayaz.ts3client.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.channel.ChannelLayout
import site.sayaz.ts3client.ui.navigation.AnimatedTopAppBar
import site.sayaz.ts3client.ui.navigation.BottomNav
import site.sayaz.ts3client.ui.navigation.BottomNavItem
import site.sayaz.ts3client.ui.navigation.ScaRoute
import site.sayaz.ts3client.ui.navigation.TopNavItem
import site.sayaz.ts3client.ui.server.ServerAction

import site.sayaz.ts3client.ui.server.ServerLayout
import site.sayaz.ts3client.ui.settings.SettingsLayout

@Composable
fun MainScaffold(mainNavController: NavController, appViewModel: AppViewModel = viewModel()) {
    val selectedIndex = remember { mutableIntStateOf(0) }

    val topNavItem = TopNavItem.getItems()
    val navController = rememberNavController()
    //animation
    val enterTransition = fadeIn(animationSpec = tween(195))
    val exitTransition = fadeOut(animationSpec = tween(195))

    Scaffold(
        topBar = {
            AnimatedTopAppBar(
                selectedIndex.intValue == 0,
                topNavItem[selectedIndex.intValue].titleID
            ) {
                ServerAction(mainNavController)
            }
            AnimatedTopAppBar(
                selectedIndex.intValue == 1,
                topNavItem[selectedIndex.intValue].titleID
            ) {

            }
            AnimatedTopAppBar(
                selectedIndex.intValue == 2,
                topNavItem[selectedIndex.intValue].titleID
            ) {

            }
        },
        bottomBar = { BottomNav(navController = navController, selectedIndex) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ScaRoute.SERVER.name,
            enterTransition = { enterTransition },
            exitTransition = { exitTransition }
        ) {
            composable(ScaRoute.SERVER.name) {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    ServerLayout(appViewModel,mainNavController)
                }
            }
            composable(ScaRoute.CHANNEL.name) {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    ChannelLayout(appViewModel)
                }
            }
            composable(ScaRoute.SETTINGS.name) {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    SettingsLayout(appViewModel)
                }
            }
        }
    }
}