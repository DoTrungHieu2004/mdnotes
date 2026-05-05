package com.hieu10.mdnotes.ui.states

import com.hieu10.mdnotes.db.models.Folder
import com.hieu10.mdnotes.db.models.Tag

enum class FoldersTagsTab { FOLDERS, TAGS }

data class FolderTagsUIState(
    val selectedTab: FoldersTagsTab = FoldersTagsTab.FOLDERS,
    val folders: List<Pair<Folder, Int>> = emptyList(),     // pair: Folder + note count
    val tags: List<Pair<Tag, Int>> = emptyList(),           // pair: Tag + note count
    val isLoading: Boolean = true
)