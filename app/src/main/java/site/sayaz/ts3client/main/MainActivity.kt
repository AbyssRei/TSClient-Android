package site.sayaz.ts3client.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.main.MainScreen
import site.sayaz.ts3client.ui.theme.TS3ClientTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TS3ClientTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel())
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val appViewModel : AppViewModel by viewModels()
        runBlocking { appViewModel.disconnectAll() }

    }
}