package site.sayaz.ts3client.ui.settings


import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import okhttp3.internal.connection.RouteSelector
import site.sayaz.ts3client.R
import site.sayaz.ts3client.ui.AppViewModel
import site.sayaz.ts3client.ui.navigation.MainRoute
import site.sayaz.ts3client.ui.theme.CustomTheme
import site.sayaz.ts3client.ui.theme.ThemeEnum
import site.sayaz.ts3client.ui.util.toast
import java.lang.Thread.sleep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen(mainNavController: NavHostController, appViewModel: AppViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.appearance)) },
                navigationIcon = {
                    IconButton(onClick = { mainNavController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Close"
                        )
                    }
                }
            )
        },

        ) {
        AppearanceScreenContent(it, appViewModel, mainNavController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreenContent(
    paddingValues: PaddingValues,
    appViewModel: AppViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val appState by appViewModel.uiState.collectAsState()
        val darkModeSelectedOption = remember {
            appState.settingsData.appearance
        }
        val selectedIndex = remember {
            mutableStateOf(
                when (darkModeSelectedOption) {
                    "system" -> 0
                    "light" -> 1
                    "dark" -> 2
                    else -> 0
                }
            )
        }
        val optionList = listOf(
            stringResource(id = R.string.system),
            stringResource(id = R.string.light),
            stringResource(id = R.string.dark)
        )
        // dark mode
        MultiChoiceSegmentedButtonRow(
            modifier = Modifier
                .padding(16.dp)
                .height(36.dp)
                .fillMaxWidth()
        ) {
            optionList.forEachIndexed { index, option ->
                SegmentedButton(
                    checked = (index == selectedIndex.value),
                    onCheckedChange = {
                        selectedIndex.value = index
                        appViewModel.changeAppearance(
                            when (index) {
                                0 -> "system"
                                1 -> "light"
                                2 -> "dark"
                                else -> "system"
                            }
                        )
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = optionList.size
                    )
                ) {
                    Text(text = option)
                }

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.theme),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        // theme
        val themeOption = listOf(
            stringResource(R.string.default_value_moren),
            stringResource(R.string.dynamic),
            stringResource(R.string.apple),
        )
        val themeSelectedOption = remember { appState.settingsData.theme }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            themeOption.forEachIndexed { index, option ->
                ThemeOption(
                    title = option,
                    modifier = Modifier
                        .padding(16.dp),
                    selected = (index == when (themeSelectedOption) {
                        "def" -> 0
                        "dynamic" -> 1
                        "apple" -> 2
                        else -> 0
                    }),
                    theme = when (index) {
                        0 -> "def"
                        1 -> "dynamic"
                        2 -> "apple"
                        else -> "def"
                    }
                ) {
                    //onclick
                    appViewModel.changeTheme(
                        when (index) {
                            0 -> "def"
                            1 -> "dynamic"
                            2 -> "apple"
                            else -> "def"
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun ThemeOption(title: String, modifier: Modifier, selected: Boolean,theme:String, onClick: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTheme(useDarkTheme = false, theme = theme, useSideEffect = false) {
            Card(
                modifier = Modifier
                    .width(90.dp)
                    .height(160.dp)
                    .selectable(
                        selected = selected,
                        onClick = { onClick() },
                    ),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder().copy(width = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                ) {
                    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
                    val onPrimaryContainer = MaterialTheme.colorScheme.onPrimaryContainer
                    val onSurface = MaterialTheme.colorScheme.onSurface
                    Canvas(modifier = Modifier.size(90.dp, 160.dp)) {
                        drawRect(
                            color = primaryContainer,
                            size = Size(size.width, size.height / 5),
                            topLeft = Offset(0f, size.height * 4 / 5)
                        )
                        drawRoundRect(
                            color = primaryContainer,
                            size = Size(size.width / 2, size.height / 3),
                            cornerRadius = CornerRadius(10f),
                            topLeft = Offset(size.width / 10, size.height / 3)
                        )
                        drawRoundRect(
                            color = onSurface,
                            size = Size(size.width / 2, size.height / 8),
                            cornerRadius = CornerRadius(20f),
                            topLeft = Offset(size.width / 10, size.height * 2 / 19)

                        )
                        drawCircle(
                            color = onPrimaryContainer,
                            radius = size.width / 9,
                            center = Offset(size.width * 4 / 5, size.height * 8 / 9)
                        )
                    }
                    RadioButton(
                        selected = selected,
                        onClick = { onClick() },
                        modifier = Modifier
                            .size(20.dp)
                            .padding(0.dp)
                            .offset(64.dp, 16.dp),
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = title, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
