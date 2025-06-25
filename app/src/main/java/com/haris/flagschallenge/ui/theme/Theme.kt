package com.haris.flagschallenge.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFE65100),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFF018786)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFF018786)
)

@Composable
fun FlagsChallengeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}