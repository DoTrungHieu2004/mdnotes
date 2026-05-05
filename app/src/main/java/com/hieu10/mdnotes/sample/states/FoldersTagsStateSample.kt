package com.hieu10.mdnotes.sample.states

import com.hieu10.mdnotes.sample.data.folderSamples
import com.hieu10.mdnotes.sample.data.sampleTags
import com.hieu10.mdnotes.ui.states.FolderTagsUIState
import com.hieu10.mdnotes.ui.states.FoldersTagsTab

val foldersState = FolderTagsUIState(
    selectedTab = FoldersTagsTab.FOLDERS,
    folders = folderSamples.map { folder ->
        Pair(folder, 0)
    },
    tags = emptyList(),
    isLoading = false
)

val tagsState = FolderTagsUIState(
    selectedTab = FoldersTagsTab.TAGS,
    folders = emptyList(),
    tags = sampleTags.map { tag ->
        Pair(tag, 0)
    },
    isLoading = false
)