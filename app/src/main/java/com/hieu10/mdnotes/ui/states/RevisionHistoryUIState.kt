package com.hieu10.mdnotes.ui.states

import com.hieu10.mdnotes.db.models.NoteRevision

data class RevisionHistoryUIState(
    val revisions: List<NoteRevision> = emptyList(),
    val expandedRevisionId: String? = null,     // which revision is expanded
    val isLoading: Boolean = true,
    val isRestoring: Boolean = false
)