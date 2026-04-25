package com.hieu10.mdnotes.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.hieu10.mdnotes.ui.theme.extensions.MarkdownColors
import com.hieu10.mdnotes.ui.theme.extensions.SemanticColors

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    error = DarkError,
    errorContainer = DarkErrorContainer,
    onError = DarkOnError,
    onErrorContainer = DarkOnErrorContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    surfaceDim = DarkSurfaceDim,
    surfaceBright = DarkSurfaceBright,
    surfaceContainerLowest = DarkSurfaceContainerLowest,
    surfaceContainerLow = DarkSurfaceContainerLow,
    surfaceContainer = DarkSurfaceContainer,
    surfaceContainerHigh = DarkSurfaceContainerHigh,
    surfaceContainerHighest = DarkSurfaceContainerHighest,
    scrim = DarkScrim,
    inverseSurface = DarkInverseSurface,
    inverseOnSurface = DarkOnInverseSurface,
    inversePrimary = DarkInversePrimary
)

val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiaryContainer,
    onTertiaryContainer = LightOnTertiaryContainer,
    error = LightError,
    errorContainer = LightErrorContainer,
    onError = LightOnError,
    onErrorContainer = LightOnErrorContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    surfaceDim = LightSurfaceDim,
    surfaceBright = LightSurfaceBright,
    surfaceContainerLowest = LightSurfaceContainerLowest,
    surfaceContainerLow = LightSurfaceContainerLow,
    surfaceContainer = LightSurfaceContainer,
    surfaceContainerHigh = LightSurfaceContainerHigh,
    surfaceContainerHighest = LightSurfaceContainerHighest,
    scrim = LightScrim,
    inverseSurface = LightInverseSurface,
    inverseOnSurface = LightOnInverseSurface,
    inversePrimary = LightInversePrimary
)

val DarkSematicColors = SemanticColors(
    success = DarkSuccess,
    onSuccess = DarkOnSuccess,
    successContainer = DarkSuccessContainer,
    warning = DarkWarning,
    onWarning = DarkOnWarning,
    warningContainer = DarkWarningContainer,
    info = DarkInfo,
    onInfo = DarkOnInfo,
    infoContainer = DarkInfoContainer,
    hint = DarkHint
)

val LightSematicColors = SemanticColors(
    success = LightSuccess,
    onSuccess = LightOnSuccess,
    successContainer = LightSuccessContainer,
    warning = LightWarning,
    onWarning = LightOnWarning,
    warningContainer = LightWarningContainer,
    info = LightInfo,
    onInfo = LightOnInfo,
    infoContainer = LightInfoContainer,
    hint = LightHint
)

val DarkMarkdownColors = MarkdownColors(
    textPrimary = DarkTextPrimary,
    textSecondary = DarkTextSecondary,
    heading = DarkHeading,
    headingStrong = DarkHeadingStrong,
    bold = DarkBold,
    italic = DarkItalic,
    link = DarkLink,
    linkVisited = DarkLinkVisited,
    codeInline = DarkCodeInline,
    codeBlockBackground = DarkCodeBlockBackground,
    codeBlockText = DarkCodeBlockText,
    quoteBar = DarkQuoteBar,
    quoteText = DarkQuoteText,
    divider = DarkDivider,
    listBullet = DarkListBullet
)

val LightMarkdownColors = MarkdownColors(
    textPrimary = LightTextPrimary,
    textSecondary = LightTextSecondary,
    heading = LightHeading,
    headingStrong = LightHeadingStrong,
    bold = LightBold,
    italic = LightItalic,
    link = LightLink,
    linkVisited = LightLinkVisited,
    codeInline = LightCodeInline,
    codeBlockBackground = LightCodeBlockBackground,
    codeBlockText = LightCodeBlockText,
    quoteBar = LightQuoteBar,
    quoteText = LightQuoteText,
    divider = LightDivider,
    listBullet = LightListBullet
)