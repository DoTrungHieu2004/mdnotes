package com.hieu10.mdnotes.sample.states

import com.hieu10.mdnotes.sample.data.noteSamples
import com.hieu10.mdnotes.ui.states.HomeState
import com.hieu10.mdnotes.ui.states.NoteWithMeta
import com.hieu10.mdnotes.ui.states.SortOrder

val homeState = HomeState(
    pinnedNotes = noteSamples
        .filter { it.isPinned }
        .map { NoteWithMeta(it, null, folderName = null) },
    allNotes = noteSamples
        .filter { !it.isPinned }
        .map { NoteWithMeta(it, null, folderName = null) },
    isLoading = false,
    sortOrder = SortOrder.LAST_MODIFIED
)

val homeStateEmpty = HomeState(
    pinnedNotes = emptyList(),
    allNotes = emptyList(),
    isLoading = false,
    sortOrder = SortOrder.LAST_MODIFIED
)