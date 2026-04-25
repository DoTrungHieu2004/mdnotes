package com.hieu10.mdnotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.hieu10.mdnotes.ui.theme.extensions.MarkdownColors
import com.hieu10.mdnotes.ui.theme.extensions.MarkdownShapes
import com.hieu10.mdnotes.ui.theme.extensions.MarkdownTypography
import com.hieu10.mdnotes.ui.theme.extensions.SemanticColors
import com.hieu10.mdnotes.ui.theme.extensions.SemanticShapes

val LocalSemanticColors = staticCompositionLocalOf<SemanticColors> {
    error("No SemanticColors provided")
}

val LocalMarkdownColors = staticCompositionLocalOf<MarkdownColors> {
    error("No MarkdownColors provided")
}

val LocalMarkdownTypography = staticCompositionLocalOf<MarkdownTypography> {
    error("No MarkdownTypography provided")
}

val LocalMarkdownShapes = staticCompositionLocalOf<MarkdownShapes> {
    error("No MarkdownShapes provided")
}

val LocalSemanticShapes = staticCompositionLocalOf<SemanticShapes> {
    error("No SemanticShapes provided")
}

@Composable
fun MDNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    val semanticColors = if (darkTheme) DarkSematicColors else LightSematicColors
    val markdownColors = if (darkTheme) DarkMarkdownColors else LightMarkdownColors

    CompositionLocalProvider(
        LocalSemanticColors provides semanticColors,
        LocalMarkdownColors provides markdownColors,
        LocalMarkdownTypography provides MarkdownTypographyDefault,
        LocalMarkdownShapes provides MarkdownShapesDefault,
        LocalSemanticShapes provides SemanticShapesDefault
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
        )
    }
}