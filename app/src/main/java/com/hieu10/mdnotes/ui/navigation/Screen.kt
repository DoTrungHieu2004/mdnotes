package com.hieu10.mdnotes.ui.navigation

sealed class Screen(val route: String) {
    // Bottom navigation
    object Main : Screen("main")
    object Home : Screen("home")
    object FoldersTags : Screen("folders_tags")
    object Calendar : Screen("calendar")
    object ProfileSettings : Screen("profile_settings")

    // Screens
    object NoteEditor : Screen("note/{noteId}") {
        fun createRoute(noteId: String) = "note/$noteId"
    }
}