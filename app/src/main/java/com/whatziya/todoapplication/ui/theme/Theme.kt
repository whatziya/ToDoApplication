package com.whatziya.todoapplication.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(

    primary = DarkColors.ColorBlue,
    secondary = DarkColors.ColorGreen,

    background = DarkColors.BackPrimary,
    onPrimary = DarkColors.LabelPrimary,
    onSecondary = DarkColors.LabelSecondary,
    onTertiary = DarkColors.LabelTertiary,

    surface = DarkColors.BackSecondary,
    onSurface = DarkColors.SupportSeparator,

    error = DarkColors.ColorRed,
    onError = DarkColors.ColorRed,

    onBackground = LightColors.ColorGray
)

private val LightColorScheme = lightColorScheme(

    primary = LightColors.ColorBlue,
    secondary = LightColors.ColorGreen,

    background = LightColors.BackPrimary,
    onPrimary = LightColors.LabelPrimary,
    onSecondary = LightColors.LabelSecondary,
    onTertiary = LightColors.LabelTertiary,

    surface = LightColors.BackSecondary,
    onSurface = LightColors.SupportSeparator,

    error = LightColors.ColorRed,
    onError = LightColors.ColorRed,

    onBackground = LightColors.ColorGray
)

@Composable
fun ToDoApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
