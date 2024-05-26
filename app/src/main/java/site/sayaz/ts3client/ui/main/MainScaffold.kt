package site.sayaz.ts3client.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.channel.ChannelAction
import site.sayaz.ts3client.ui.channel.ChannelLayout
import site.sayaz.ts3client.ui.navigation.AnimatedTopAppBar
import site.sayaz.ts3client.ui.navigation.BottomNav
import site.sayaz.ts3client.ui.navigation.MainRoute
import site.sayaz.ts3client.ui.navigation.ScaRoute
import site.sayaz.ts3client.ui.navigation.TopNavItem
import site.sayaz.ts3client.ui.server.ServerAction

import site.sayaz.ts3client.ui.server.ServerLayout
import site.sayaz.ts3client.ui.settings.SettingsLayout
import site.sayaz.ts3client.ui.util.ErrorNotifier

@Composable
fun MainScaffold(mainNavController: NavController, appViewModel: AppViewModel = viewModel()) {
    val selectedIndex = remember { mutableIntStateOf(0) }

    val topNavItem = TopNavItem.getItems()
    val navController = rememberNavController()
    //animation
    val enterTransition = fadeIn(animationSpec = tween(195))
    val exitTransition = fadeOut(animationSpec = tween(195))

    // snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    val appState by appViewModel.uiState.collectAsState()

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
                ChannelAction(appViewModel, appState, appViewModel::disconnect)
            }
            AnimatedTopAppBar(
                selectedIndex.intValue == 2,
                R.string.space
            ) {}
        },
        bottomBar = { BottomNav(navController = navController, selectedIndex) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            AnimatedVisibility(visible = selectedIndex.intValue == 0,
                enter = enterTransition, exit = exitTransition) {
                FloatingActionButton(onClick = { mainNavController.navigate(MainRoute.ADD_SERVER.name) }) {
                    Icon(Icons.Filled.Add, contentDescription = "add Server")
                }
            }
        }

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
                    ServerLayout(appViewModel = appViewModel)
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
                    SettingsLayout(appViewModel,mainNavController)
                }
            }
        }
        ErrorNotifier(appViewModel = appViewModel, snackbarHostState = snackbarHostState)
    }
}