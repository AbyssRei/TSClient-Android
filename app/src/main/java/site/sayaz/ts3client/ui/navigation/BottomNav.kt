package site.sayaz.ts3client.ui.navigation

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.main.MainScreen

@Composable
fun BottomNav(navController: NavController, title: MutableState<Int>, actions: MutableState<@Composable () -> Unit>){
    // 选中的导航项
    var selectedItem by remember { mutableIntStateOf(0) }
    // 导航项
    val items = NavItem.getItems()
    // 导航栈
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // 当前目的地
    val currentDestination = navBackStackEntry?.destination

    // 添加监听器
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            items.find { it.route == destination.route }?.let { item ->
                selectedItem = items.indexOf(item)
                actions.value = item.actions
                title.value = item.titleID
            }
        }
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
    // 底部导航栏
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = null)},
                label = { Text(text = stringResource(id = item.titleID)) },
                // 选中状态 与当前导航同步
                selected = currentDestination?.hierarchy?.any { it.route == item.route }?:false,
                onClick = {
                    // 已选中则不切换
                    if (selectedItem != index) navController.navigate(route = item.route)
                    selectedItem = index
                    actions.value = item.actions
                    // 顶部标题
                    title.value = item.titleID
                }
            )
        }
    }
}