package com.hieu10.mdnotes.ui.states

import com.hieu10.mdnotes.R

data class NoteEditorUIState(
    val noteId: String = "new",
    val title: String = "",
    val content: String = "",
    val isPreview: Boolean = false,
    val saveStatus: Int = R.string.unsaved,
    val wordCount: Int = 0,
    val tags: List<String> = emptyList(),
    val folderName: String? = null,
    val backlinkCount: Int = 0,
    val isPinned: Boolean = false,
    val isFavourite: Boolean = false,
    val isLocked: Boolean = false
)