package site.sayaz.ts3client.main



import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import site.sayaz.ts3client.R
import site.sayaz.ts3client.settings.SettingsDataDao
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.main.MainScreen
import site.sayaz.ts3client.ui.theme.CustomTheme
import site.sayaz.ts3client.ui.util.toast
import java.util.Locale
import javax.inject.Inject


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

        if (savedInstanceState != null) {
            viewmodel.restoreState(savedInstanceState)
        }
        // read settings
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                initSettings()
            }
        }

        setContent {
            val appState by viewmodel.uiState.collectAsState()
            val useDarkTheme = when (appState.settingsData.appearance) {
                "dark" -> true
                "light" -> false
                else -> isSystemInDarkTheme()
            }
            CustomTheme(
                useDarkTheme = useDarkTheme,
                theme = appState.settingsData.theme
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    viewmodel = viewModel()
                    MainScreen(viewmodel)
                }
            }
        }
    }

    private suspend fun initSettings() {
        val settingsData = settingsDataDao.getSettingsData()
        //sleep
        if (settingsData.preventSleepDuringConnection) {
            Log.d("MainActivity", "keep screen on")
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        //language
        val resources: Resources = this@MainActivity.resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        when (settingsData.language) {
            "zh" -> {
                Log.d("MainActivity", "set language to Chinese")
                config.locale = Locale("zh")
            }

            "en" -> {
                Log.d("MainActivity", "set language to English")
                config.locale = Locale("en")
            }

            else -> {
                Log.d("MainActivity", "set language to default")
                config.locale = Locale.getDefault()
            }
        }
        resources.updateConfiguration(config, dm)
        //appearance

        //theme

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