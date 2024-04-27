package site.sayaz.ts3client.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import kotlinx.coroutines.runBlocking
import site.sayaz.ts3client.db.AppDB
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.main.MainScreen
import site.sayaz.ts3client.ui.theme.TS3ClientTheme
import site.sayaz.ts3client.ui.util.toast
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")
        XXPermissions.with(this)
            .permission(Permission.RECORD_AUDIO)
            .permission(Permission.NOTIFICATION_SERVICE)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {}
                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        toast(applicationContext, "被永久拒绝授权，请手动授予录音和日历权限")
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(applicationContext, permissions)
                    } else {
                        //TODO
                        toast(applicationContext, "获取录音和日历权限失败")
                    }
                }
            })


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
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }
    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }
    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity", "onRestart")
    }
}