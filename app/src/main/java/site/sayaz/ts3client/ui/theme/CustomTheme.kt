package site.sayaz.ts3client.ui.theme

import androidx.compose.runtime.Composable
import site.sayaz.ts3client.ui.theme.defaultTheme.DefaultTheme
import site.sayaz.ts3client.ui.theme.redAppleTheme.RedAppleTheme

@Composable
fun CustomTheme(
    useDarkTheme : Boolean,
    theme : String,
    useSideEffect : Boolean = true,
    content: @Composable () -> Unit,
) {
    // def,dynamic,apple
    when(theme){
        "def" -> DefaultTheme(useDarkTheme = useDarkTheme, content = content, useDynamicTheme = false,useSideEffect = useSideEffect)
        "dynamic" -> DefaultTheme(useDarkTheme = useDarkTheme, content = content, useDynamicTheme = true,useSideEffect = useSideEffect)
        "apple" -> RedAppleTheme(darkTheme = useDarkTheme, content = content, dynamicColor = false, useSideEffect = useSideEffect)
        else -> DefaultTheme(
            useDarkTheme = useDarkTheme,
            content = content,
            useDynamicTheme = false,
            useSideEffect = useSideEffect
        )
    }
}