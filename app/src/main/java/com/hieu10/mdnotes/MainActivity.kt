package com.hieu10.mdnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.hieu10.mdnotes.di.LocalAppContainer
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppRoot()
        }
    }
}

@Composable
fun AppRoot() {
    val app = LocalContext.current.applicationContext as MDNotesApp

    CompositionLocalProvider(
        LocalAppContainer provides app.container
    ) {
        MDNotesTheme {

        }
    }
}