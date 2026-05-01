package com.hieu10.mdnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hieu10.mdnotes.di.LocalAppContainer
import com.hieu10.mdnotes.ui.navigation.Screen
import com.hieu10.mdnotes.ui.screens.MainScreen
import com.hieu10.mdnotes.ui.screens.placeholders.PlaceholderScreen
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
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.Main.route
            ) {
                composable(Screen.Main.route) {
                    MainScreen(navController = navController)
                }
                composable(
                    route = Screen.NoteEditor.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable
                    // Placeholder for NoteEditorScreen
                    PlaceholderScreen("Note Editor: $noteId")
                }
            }
        }
    }
}