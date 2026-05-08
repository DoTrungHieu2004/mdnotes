package com.hieu10.mdnotes.sample.states

import androidx.compose.ui.graphics.Color
import com.hieu10.mdnotes.sample.data.noteSamples
import com.hieu10.mdnotes.ui.states.NoteWithMeta
import com.hieu10.mdnotes.ui.states.NotesByFolderUIState
import com.hieu10.mdnotes.ui.states.SortOrder

val notesByFolderState = NotesByFolderUIState(
    folderName = "Personal",
    folderColor = Color(0xFF14B8A6),
    pinnedNotes = noteSamples
        .filter { it.isPinned && it.title.contains("Weekly") }
        .map { NoteWithMeta(it, folderColor = Color(0xFF14B8A6), folderName = null) },
    allNotes = noteSamples
        .filter { !it.isPinned }
        .take(4)
        .map { NoteWithMeta(it, folderColor = Color(0xFF14B8A6), folderName = null) },
    isLoading = false,
    sortOrder = SortOrder.LAST_MODIFIED
)

val notesByFolderEmptyState = NotesByFolderUIState(
    folderName = "Work",
    folderColor = Color(0xFF4F46E5),
    pinnedNotes = emptyList(),
    allNotes = emptyList(),
    isLoading = false,
    sortOrder = SortOrder.LAST_MODIFIED
)