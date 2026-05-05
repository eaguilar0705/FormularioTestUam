package com.example.formulariotestuam.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val UamColorScheme = lightColorScheme(
    primary = UamPrimary,
    onPrimary = White,
    secondary = UamSuccess,
    onSecondary = White,
    tertiary = UamWarning,
    background = UamBackground,
    onBackground = UamTextPrimary,
    surface = UamSurface,
    onSurface = UamTextPrimary
)

@Composable
fun FormularioTestUamTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = UamColorScheme,
        typography = Typography,
        content = content
    )
}