package site.sayaz.ts3client.ui.settings

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import site.sayaz.ts3client.ui.AppViewModel
import java.io.File
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.coroutineScope
import site.sayaz.ts3client.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenSourceLicenceScreen(mainNavController: NavHostController, appViewModel: AppViewModel) {
    val context = LocalContext.current
    val licenses = remember { mutableStateOf(listOf<License>()) }

    LaunchedEffect(key1 = Unit) {
        licenses.value = parseLicenses(context)
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.open_source_licences)) },
                navigationIcon = {
                    IconButton(onClick = { mainNavController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ){
        LazyColumn (
            modifier = Modifier.padding(it)
        ){
            items(licenses.value) { license ->
                Text(text = "Group: ${license.group}")
                Text(text = "Name: ${license.name}")
                Text(text = "Version: ${license.version}")
                Text(text = "License: ${license.license}")
                HorizontalDivider()
            }
        }
    }

}

suspend fun parseLicenses(context : Context): List<License> {
    return withContext(Dispatchers.IO) {
        val html = context.assets.open("index.html").bufferedReader().use { it.readText() }
        val doc = Jsoup.parse(html)
        val licenses = mutableListOf<License>()

        val elements = doc.select("hr")
        for (element in elements) {
            val parts = element.nextElementSibling()?.ownText()?.split(" ")
            val group = parts?.getOrNull(0)
            val name = parts?.getOrNull(1)
            val version = parts?.getOrNull(2)
            val license = element.nextElementSibling()?.nextElementSibling()?.nextElementSibling()?.text()
            if (group != null && name != null && version != null) {
                licenses.add(License(group, name, version, license?:""))
            }
        }
        licenses
    }
}

data class License(
    val group: String,
    val name: String,
    val version: String,
    val license: String
)