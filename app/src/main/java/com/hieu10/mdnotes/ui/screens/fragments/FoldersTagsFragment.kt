package com.hieu10.mdnotes.ui.screens.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.sample.states.foldersState
import com.hieu10.mdnotes.sample.states.tagsState
import com.hieu10.mdnotes.ui.components.card.FolderCard
import com.hieu10.mdnotes.ui.components.card.TagCard
import com.hieu10.mdnotes.ui.components.states.EmptyFoldersState
import com.hieu10.mdnotes.ui.components.states.EmptyTagsState
import com.hieu10.mdnotes.ui.states.FoldersTagsTab
import com.hieu10.mdnotes.ui.states.FolderTagsUIState
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.viewmodel.FoldersTagsViewModel

@Composable
fun FoldersTagsFragment(
    viewModel: FoldersTagsViewModel,
    onFolderClick: (String) -> Unit,
    onTagClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FoldersTagsContent(
        state = state,
        onTabSelected = viewModel::onTabSelected,
        onFolderClick = onFolderClick,
        onFolderRename = viewModel::renameFolder,
        onFolderChangeColor = viewModel::changeFolderColor,
        onFolderDelete = viewModel::deleteFolder,
        onTagClick = onTagClick,
        onTagRename = viewModel::renameTag,
        onTagDelete = viewModel::deleteTag,
        onCreateFolder = viewModel::showCreateFolderDialog,
        onCreateTag = viewModel::showCreateTagDialog
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoldersTagsContent(
    state: FolderTagsUIState,
    onTabSelected: (FoldersTagsTab) -> Unit,
    onFolderClick: (String) -> Unit,             // folder id
    onFolderRename: (String) -> Unit,
    onFolderChangeColor: (String) -> Unit,
    onFolderDelete: (String) -> Unit,
    onTagClick: (String) -> Unit,                // tag id
    onTagRename: (String) -> Unit,
    onTagDelete: (String) -> Unit,
    onCreateFolder: () -> Unit,
    onCreateTag: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_bar_folders_tags)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = state.selectedTab.ordinal) {
                Tab(
                    selected = state.selectedTab == FoldersTagsTab.FOLDERS,
                    onClick = { onTabSelected(FoldersTagsTab.FOLDERS) },
                    text = { Text(text = stringResource(id = R.string.tab_row_folders)) }
                )
                Tab(
                    selected = state.selectedTab == FoldersTagsTab.TAGS,
                    onClick = { onTabSelected(FoldersTagsTab.TAGS) },
                    text = { Text(text = stringResource(id = R.string.tab_row_tags)) }
                )
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                when (state.selectedTab) {
                    FoldersTagsTab.FOLDERS -> {
                        if (state.folders.isEmpty()) {
                            EmptyFoldersState(onCreateClick = onCreateFolder)
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                items(items = state.folders, key = { (folder, _) -> folder.id }) { (folder, count) ->
                                    FolderCard(
                                        folder = folder,
                                        noteCount = count,
                                        onClick = { onFolderClick(folder.id) },
                                        onRename = { onFolderRename(folder.id) },
                                        onChangeColor = { onFolderChangeColor(folder.id) },
                                        onDelete = { onFolderDelete(folder.id) }
                                    )
                                }
                            }
                        }
                    }
                    FoldersTagsTab.TAGS -> {
                        if (state.tags.isEmpty()) {
                            EmptyTagsState(onCreateClick = onCreateTag)
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                items(items = state.tags, key = { (tag, _) -> tag.tagId }) { (tag, count) ->
                                    TagCard(
                                        tag = tag,
                                        noteCount = count,
                                        onClick = { onTagClick(tag.tagId) },
                                        onRename = { onTagRename(tag.tagId) },
                                        onDelete = { onTagDelete(tag.tagId) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFolderScreenLight() {
    MDNotesTheme(darkTheme = false) {
        FoldersTagsContent(
            state = foldersState,
            onTabSelected = { FoldersTagsTab.FOLDERS },
            onFolderClick = {},
            onFolderRename = {},
            onFolderChangeColor = {},
            onFolderDelete = {},
            onTagClick = {},
            onTagRename = {},
            onTagDelete = {},
            onCreateFolder = {},
            onCreateTag = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFolderScreenDark() {
    MDNotesTheme(darkTheme = true) {
        FoldersTagsContent(
            state = foldersState,
            onTabSelected = {},
            onFolderClick = {},
            onFolderRename = {},
            onFolderChangeColor = {},
            onFolderDelete = {},
            onTagClick = {},
            onTagRename = {},
            onTagDelete = {},
            onCreateFolder = {},
            onCreateTag = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTagScreenLight() {
    MDNotesTheme(darkTheme = false) {
        FoldersTagsContent(
            state = tagsState,
            onTabSelected = {},
            onFolderClick = {},
            onFolderRename = {},
            onFolderChangeColor = {},
            onFolderDelete = {},
            onTagClick = {},
            onTagRename = {},
            onTagDelete = {},
            onCreateFolder = {},
            onCreateTag = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTagScreenDark() {
    MDNotesTheme(darkTheme = true) {
        FoldersTagsContent(
            state = tagsState,
            onTabSelected = { FoldersTagsTab.TAGS },
            onFolderClick = {},
            onFolderRename = {},
            onFolderChangeColor = {},
            onFolderDelete = {},
            onTagClick = {},
            onTagRename = {},
            onTagDelete = {},
            onCreateFolder = {},
            onCreateTag = {}
        )
    }
}