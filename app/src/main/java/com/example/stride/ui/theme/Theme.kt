package com.example.stride.ui.theme

import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF6F61), // Coral Red
    onPrimary = Color.White,    // Text on Primary
    background = Color(0xFF121212), // Dark Gray/Black
    surface = Color(0xFF1E1E1E),    // Slightly lighter gray for surfaces
    onBackground = Color.White, // Text on Background
    onSurface = Color.White     // Text on Surface
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF6F61), // Coral Red
    onPrimary = Color.Black,    // Text on Primary
    background = Color.White, // Dark Gray/Black
    surface = Color(0xFF1E1E1E),    // Slightly lighter gray for surfaces
    onBackground = Color.Black, // Text on Background
    onSurface = Color.Black     // Text on Surface
)

@Composable
fun StrideTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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