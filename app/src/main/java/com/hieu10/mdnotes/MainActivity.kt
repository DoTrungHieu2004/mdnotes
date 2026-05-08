package com.hieu10.mdnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hieu10.mdnotes.di.LocalAppContainer
import com.hieu10.mdnotes.ui.navigation.Screen
import com.hieu10.mdnotes.ui.screens.MainScreen
import com.hieu10.mdnotes.ui.screens.NoteEditorScreen
import com.hieu10.mdnotes.ui.screens.NotesByFolderScreen
import com.hieu10.mdnotes.ui.screens.NotesByTagScreen
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.viewmodel.NoteEditorViewModel
import com.hieu10.mdnotes.viewmodel.NotesByFolderViewModel
import com.hieu10.mdnotes.viewmodel.NotesByTagViewModel

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
                    val container = LocalAppContainer.current
                    val viewModel = remember(noteId) {
                        NoteEditorViewModel(
                            noteId = noteId,
                            noteRepository = container.noteRepository,
                            folderRepository = container.folderRepository,
                            tagRepository = container.tagRepository
                        )
                    }

                    NoteEditorScreen(
                        noteId = noteId,
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(
                    route = Screen.NotesByFolder.route,
                    arguments = listOf(navArgument("folderId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val folderId = backStackEntry.arguments?.getString("folderId") ?: return@composable
                    val container = LocalAppContainer.current
                    val viewModel = remember(folderId) {
                        NotesByFolderViewModel(
                            folderId = folderId,
                            noteRepository = container.noteRepository,
                            folderRepository = container.folderRepository
                        )
                    }

                    NotesByFolderScreen(
                        folderId = folderId,
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onNoteClick = { noteId -> navController.navigate(Screen.NoteEditor.createRoute(noteId)) },
                        onSearchClick = { /* navigate to search */ },
                        onNewNoteClick = { navController.navigate(Screen.NoteEditor.createRoute("new")) }
                    )
                }
                composable(
                    route = Screen.NotesByTag.route,
                    arguments = listOf(navArgument("tagId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val tagId = backStackEntry.arguments?.getString("tagId") ?: return@composable
                    val container = LocalAppContainer.current
                    val viewModel = remember(tagId) {
                        NotesByTagViewModel(
                            tagId = tagId,
                            noteRepository = container.noteRepository,
                            tagRepository = container.tagRepository
                        )
                    }

                    NotesByTagScreen(
                        tagId = tagId,
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onNoteClick = { noteId -> navController.navigate(Screen.NoteEditor.createRoute(noteId)) },
                        onSearchClick = { /* navigate to search */ },
                        onNewNoteClick = { navController.navigate(Screen.NoteEditor.createRoute("new")) }
                    )
                }
            }
        }
    }
}