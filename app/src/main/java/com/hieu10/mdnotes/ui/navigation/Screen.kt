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
    object NotesByFolder : Screen("notes_by_folder/{folderId}") {
        fun createRoute(folderId: String) = "notes_by_folder/$folderId"
    }
    object NotesByTag : Screen("notes_by_tag/{tagId}") {
        fun createRoute(tagId: String) = "notes_by_tag/$tagId"
    }
}