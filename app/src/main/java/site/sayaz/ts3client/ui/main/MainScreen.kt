package site.sayaz.ts3client.ui.main

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.navigation.MainRoute
import site.sayaz.ts3client.ui.server.AddServerScreen

@Composable
fun MainScreen(
    appViewModel : AppViewModel
){
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = MainRoute.MAIN.name,
        enterTransition = { slideInHorizontally(animationSpec = tween(300), initialOffsetX = {it/2}) + fadeIn(animationSpec = tween(195)) },
        exitTransition = { slideOutHorizontally(animationSpec = tween(300), targetOffsetX = {it/2}) + fadeOut(animationSpec = tween(195))}
        ){
        composable(MainRoute.MAIN.name,
            enterTransition = { slideInHorizontally(animationSpec = tween(300), initialOffsetX = {-it/2}) + fadeIn(animationSpec = tween(195))},
            exitTransition = { slideOutHorizontally(animationSpec = tween(300), targetOffsetX = {-it/2}) + fadeOut(animationSpec = tween(195))}
            ){
            MainScaffold(mainNavController,appViewModel)
        }
        composable(MainRoute.ADD_SERVER.name){
            Log.d("MainScreen",MainRoute.ADD_SERVER.name)
            AddServerScreen(mainNavController,appViewModel)
        }
    }
}

