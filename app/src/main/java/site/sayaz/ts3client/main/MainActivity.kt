package site.sayaz.ts3client.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import site.sayaz.ts3client.R
import site.sayaz.ts3client.db.AppDB
import site.sayaz.ts3client.settings.SettingsDataDao
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.main.MainScreen
import site.sayaz.ts3client.ui.theme.TS3ClientTheme
import site.sayaz.ts3client.ui.util.toast
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var viewmodel: AppViewModel
    @Inject lateinit var settingsDataDao: SettingsDataDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProvider(this)[AppViewModel::class.java]
        Log.d("MainActivity", "onCreate")
        XXPermissions.with(this)
            .permission(Permission.RECORD_AUDIO)
            .permission(Permission.NOTIFICATION_SERVICE)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {}
                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        toast(
                            applicationContext,
                            this@MainActivity.getString(R.string.permission_denied_forever)
                        )
                    } else {
                        toast(
                            applicationContext,
                            this@MainActivity.getString(R.string.permission_denied)
                        )
                    }
                }
            })


        setContent {
            TS3ClientTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    viewmodel = viewModel()
                    MainScreen(viewmodel)
                }
            }
        }

        // keep screen awake
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (settingsDataDao.getSettingsData().preventSleepDuringConnection){
                    Log.d("MainActivity", "keep screen on")
                    window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
        if (!isChangingConfigurations) {
            viewmodel.disconnect()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MainActivity", "onSaveInstanceState")
        viewmodel.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("MainActivity", "onRestoreInstanceState")
        viewmodel.restoreState(savedInstanceState)
    }

}