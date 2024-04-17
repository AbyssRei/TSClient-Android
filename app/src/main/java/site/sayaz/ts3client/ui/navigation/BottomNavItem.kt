package site.sayaz.ts3client.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.server.ServerAction

/**
 * 上下导航栏信息定义
 * @param titleID 标题资源ID
 * @param icon 底部导航图标
 */
class BottomNavItem(val titleID: Int, val icon: Int, val route : String){
    companion object {
        private val items = listOf(
            BottomNavItem(R.string.server, R.drawable.dns, ScaRoute.SERVER.name),
            BottomNavItem(R.string.channel, R.drawable.mic, ScaRoute.CHANNEL.name),
            BottomNavItem(R.string.settings, R.drawable.settings, ScaRoute.SETTINGS.name),
        )
        fun getItems() = items
    }
}



