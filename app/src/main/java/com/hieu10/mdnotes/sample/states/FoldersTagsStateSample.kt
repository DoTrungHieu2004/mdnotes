package com.hieu10.mdnotes.sample.states

import com.hieu10.mdnotes.db.models.FolderWithNoteCount
import com.hieu10.mdnotes.db.models.TagWithNoteCount
import com.hieu10.mdnotes.sample.data.folderSamples
import com.hieu10.mdnotes.sample.data.tagSamples
import com.hieu10.mdnotes.ui.states.FolderTagsUIState
import com.hieu10.mdnotes.ui.states.FoldersTagsTab

val foldersState = FolderTagsUIState(
    selectedTab = FoldersTagsTab.FOLDERS,
    folders = folderSamples.map { folder ->
        FolderWithNoteCount(
            folder = folder,
            noteCount = (1..10).random()
        )
    },
    tags = emptyList(),
    isLoading = false
)

val tagsState = FolderTagsUIState(
    selectedTab = FoldersTagsTab.TAGS,
    folders = emptyList(),
    tags = tagSamples.map { tag ->
        TagWithNoteCount(
            tag = tag,
            noteCount = (1..10).random()
        )
    },
    isLoading = false
)