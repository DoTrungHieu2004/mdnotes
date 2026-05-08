package com.hieu10.mdnotes.ui.states

data class NotesByTagUIState(
    val tagName: String = "",
    val pinnedNotes: List<NoteWithMeta> = emptyList(),
    val allNotes: List<NoteWithMeta> = emptyList(),
    val isLoading: Boolean = true,
    val sortOrder: SortOrder = SortOrder.LAST_MODIFIED
)
