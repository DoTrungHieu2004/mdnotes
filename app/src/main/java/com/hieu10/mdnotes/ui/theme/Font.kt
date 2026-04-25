package com.hieu10.mdnotes.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.hieu10.mdnotes.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val InterFont = FontFamily(
    Font(
        googleFont = GoogleFont("Inter"),
        fontProvider = provider
    )
)

val SerifFont = FontFamily(
    Font(
        googleFont = GoogleFont("Source Serif 4"),
        fontProvider = provider
    )
)

val MonoFont = FontFamily(
    Font(
        googleFont = GoogleFont("JetBrains Mono"),
        fontProvider = provider
    )
)