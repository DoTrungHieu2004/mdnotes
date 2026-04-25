package com.hieu10.mdnotes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hieu10.mdnotes.ui.theme.extensions.MarkdownTypography

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFont,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)

val MarkdownTypographyDefault = MarkdownTypography(
    h1 = TextStyle(
        fontFamily = SerifFont,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp
    ),
    h2 = TextStyle(
        fontFamily = SerifFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    h3 = TextStyle(
        fontFamily = SerifFont,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    body = TextStyle(
        fontFamily = SerifFont,
        fontSize = 16.sp,
        lineHeight = 26.sp // 👈 readability boost
    ),
    bold = TextStyle(
        fontFamily = SerifFont,
        fontWeight = FontWeight.Bold
    ),
    italic = TextStyle(
        fontFamily = SerifFont,
        fontStyle = FontStyle.Italic
    ),
    code = TextStyle(
        fontFamily = MonoFont,
        fontSize = 14.sp
    ),
    quote = TextStyle(
        fontFamily = SerifFont,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp
    )
)