package site.sayaz.ts3client.ui.main

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.navigation.MainRoute
import site.sayaz.ts3client.ui.server.AddServerScreen
import site.sayaz.ts3client.ui.util.ErrorNotifier

@Composable
fun MainScreen(
    appViewModel: AppViewModel
) {
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = MainRoute.MAIN.name,
        enterTransition = { slideInHorizontally(tween(300)) { it / 2 } + fadeIn(tween(195)) },
        exitTransition = { slideOutHorizontally(tween(300)) { it / 2 } + fadeOut(tween(195)) }
    ) {
        composable(MainRoute.MAIN.name,
            enterTransition = { slideInHorizontally(tween(300)) { -it / 2 } + fadeIn(tween(195)) },
            exitTransition = { slideOutHorizontally(tween(300)) { -it / 2 } + fadeOut(tween(195)) }
        ) {
            MainScaffold(mainNavController, appViewModel)
        }
        composable(MainRoute.ADD_SERVER.name) {
            Log.d("MainScreen", MainRoute.ADD_SERVER.name)
            AddServerScreen(mainNavController, appViewModel)
        }
    }

}

