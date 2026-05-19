package com.hieu10.mdnotes.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hieu10.mdnotes.MDNotesApp
import com.hieu10.mdnotes.di.LocalAppContainer
import com.hieu10.mdnotes.ui.screens.MainScreen
import com.hieu10.mdnotes.ui.screens.NoteEditorScreen
import com.hieu10.mdnotes.ui.screens.NotesByFolderScreen
import com.hieu10.mdnotes.ui.screens.NotesByTagScreen
import com.hieu10.mdnotes.ui.screens.RevisionHistoryScreen
import com.hieu10.mdnotes.ui.screens.SearchScreen
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.viewmodel.NoteEditorViewModel
import com.hieu10.mdnotes.viewmodel.NotesByFolderViewModel
import com.hieu10.mdnotes.viewmodel.NotesByTagViewModel
import com.hieu10.mdnotes.viewmodel.RevisionHistoryViewModel
import com.hieu10.mdnotes.viewmodel.SearchViewModel

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
                    val app = LocalContext.current.applicationContext as MDNotesApp

                    // Factory
                    val viewModel = viewModel<NoteEditorViewModel>(
                        factory = object : ViewModelProvider.Factory {
                            @Suppress("UNCHECKED_CAST")
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return NoteEditorViewModel(
                                    application = app,
                                    noteId = noteId,
                                    noteRepository = app.container.noteRepository,
                                    folderRepository = app.container.folderRepository,
                                    tagRepository = app.container.tagRepository
                                ) as T
                            }
                        }
                    )

                    NoteEditorScreen(
                        noteId = noteId,
                        viewModel = viewModel,
                        onNavigateToRevisions = {
                            navController.navigate(Screen.RevisionHistory.createRoute(noteId))
                        },
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
                        onSearchClick = { navController.navigate(Screen.Search.route) },
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
                        onSearchClick = { navController.navigate(Screen.Search.route) },
                        onNewNoteClick = { navController.navigate(Screen.NoteEditor.createRoute("new")) }
                    )
                }
                composable(
                    route = Screen.Search.route,
                    enterTransition = {
                        fadeIn(animationSpec = tween(300)) + scaleIn(initialScale = 0.8f, animationSpec = tween(300))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 0.8f, animationSpec = tween(300))
                    }
                ) {
                    val container = LocalAppContainer.current
                    val viewModel = remember { SearchViewModel(noteRepository = container.noteRepository) }

                    SearchScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onNoteClick = { noteId -> navController.navigate(Screen.NoteEditor.createRoute(noteId)) }
                    )
                }
                composable(
                    route = Screen.RevisionHistory.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable
                    val app = LocalContext.current.applicationContext as MDNotesApp

                    // Factory to pass parameters to ViewModel
                    val viewModel = viewModel<RevisionHistoryViewModel>(
                        factory = object : ViewModelProvider.Factory {
                            @Suppress("UNCHECKED_CAST")
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return RevisionHistoryViewModel(app, noteId, app.container.noteRepository) as T
                            }
                        }
                    )

                    RevisionHistoryScreen(
                        noteId = noteId,
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}