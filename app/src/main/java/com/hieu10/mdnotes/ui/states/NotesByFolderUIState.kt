package com.hieu10.mdnotes.ui.states

import androidx.compose.ui.graphics.Color

data class NotesByFolderUIState(
    val folderName: Any = "",
    val folderColor: Color? = null,
    val pinnedNotes: List<NoteWithMeta> = emptyList(),
    val allNotes: List<NoteWithMeta> = emptyList(),
    val isLoading: Boolean = true,
    val sortOrder: SortOrder = SortOrder.LAST_MODIFIED
)