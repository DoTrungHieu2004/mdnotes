package com.hieu10.mdnotes.sample.states

import com.hieu10.mdnotes.sample.data.noteSamples
import com.hieu10.mdnotes.ui.states.NoteWithMeta
import com.hieu10.mdnotes.ui.states.NotesByTagUIState
import com.hieu10.mdnotes.ui.states.SortOrder

val notesByTagState = NotesByTagUIState(
    tagName = "kotlin",
    pinnedNotes = noteSamples
        .filter { it.isPinned && it.title.contains("Kotlin") }
        .map { NoteWithMeta(it, folderColor = null, folderName = null) },
    allNotes = noteSamples
        .filter { !it.isPinned }
        .take(3)
        .map { NoteWithMeta(it, folderColor = null, folderName = null) },
    isLoading = false,
    sortOrder = SortOrder.LAST_MODIFIED
)

val notesByTagEmptyState = NotesByTagUIState(
    tagName = "python",
    pinnedNotes = emptyList(),
    allNotes = emptyList(),
    isLoading = false,
    sortOrder = SortOrder.LAST_MODIFIED
)