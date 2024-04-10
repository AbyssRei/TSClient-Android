package site.sayaz.ts3client.ui.navigation

import androidx.compose.runtime.Composable
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.server.ServerAction

/**
 * 上下导航栏信息定义
 * @param route 路由
 * @param titleID 标题资源ID
 * @param icon 底部导航图标
 * @param actions 顶部导航栏操作
 */
class NavItem(val route: String, val titleID: Int, val icon: Int, val actions: @Composable () -> Unit = {}){

    companion object {
        private val items = listOf(
            NavItem("server",R.string.server, R.drawable.dns) { ServerAction() },
            NavItem("channels",R.string.channel, R.drawable.mic),
            NavItem("settings",R.string.settings, R.drawable.settings),
        )
        fun getItems() = items
    }
}



