package com.hieu10.mdnotes.ui.states

import com.hieu10.mdnotes.db.models.FolderWithNoteCount
import com.hieu10.mdnotes.db.models.TagWithNoteCount

enum class FoldersTagsTab { FOLDERS, TAGS }

data class FolderTagsUIState(
    val selectedTab: FoldersTagsTab = FoldersTagsTab.FOLDERS,
    val folders: List<FolderWithNoteCount> = emptyList(),     // pair: Folder + note count
    val tags: List<TagWithNoteCount> = emptyList(),           // pair: Tag + note count
    val isLoading: Boolean = true
)