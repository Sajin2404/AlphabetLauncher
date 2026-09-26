package com.sajin.alphabetlauncher.ui.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    secondary = Color.LightGray,
    tertiary = Color.Gray,
    background = Color.Black,
    surface = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Black,
    secondary = Color.DarkGray,
    tertiary = Color.Gray,
    background = Color.White,
    surface = Color.White
)

@Composable
fun AlphabetLauncherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}