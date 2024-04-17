package site.sayaz.ts3client.ui.navigation

import site.sayaz.ts3client.R

class TopNavItem (val titleID: Int){
    companion object{
        private val items = listOf(
            TopNavItem(R.string.server),
            TopNavItem(R.string.channel),
            TopNavItem(R.string.settings),
        )
        fun getItems() = items
    }
}
