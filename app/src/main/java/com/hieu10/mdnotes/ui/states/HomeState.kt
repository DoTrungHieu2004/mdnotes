package com.hieu10.mdnotes.ui.states

import androidx.compose.ui.graphics.Color
import com.hieu10.mdnotes.db.models.Note

data class HomeState(
    val pinnedNotes: List<NoteWithMeta> = emptyList(),
    val allNotes: List<NoteWithMeta> = emptyList(),
    val isLoading: Boolean = true,
    val isSearchActive: Boolean = false,
    val sortOrder: SortOrder = SortOrder.LAST_MODIFIED
)

data class NoteWithMeta(
    val note: Note,
    val folderColor: Color?,
    val folderName: String?
)

enum class SortOrder {
    LAST_MODIFIED, CREATED_DATE, TITLE_AZ, TITLE_ZA
}