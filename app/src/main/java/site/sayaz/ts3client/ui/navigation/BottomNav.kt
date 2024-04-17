package site.sayaz.ts3client.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNav(navController: NavController, selectedItem : MutableIntState){
    // 导航项
    val items = BottomNavItem.getItems()
    // 导航栈
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // 当前目的地
    val currentDestination = navBackStackEntry?.destination

    // 添加监听器
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            items.find { it.route == destination.route }?.let { item ->
                selectedItem.intValue = items.indexOf(item)
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
                    if (selectedItem.intValue != index) navController.navigate(route = item.route)
                    selectedItem.intValue = index
                }
            )
        }
    }
}