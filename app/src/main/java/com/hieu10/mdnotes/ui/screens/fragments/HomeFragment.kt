package com.hieu10.mdnotes.ui.screens.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.sample.states.homeState
import com.hieu10.mdnotes.sample.states.homeStateEmpty
import com.hieu10.mdnotes.ui.components.card.NoteCard
import com.hieu10.mdnotes.ui.components.card.SwipeableNoteCard
import com.hieu10.mdnotes.ui.components.states.EmptyNoteState
import com.hieu10.mdnotes.ui.states.HomeState
import com.hieu10.mdnotes.ui.states.NoteWithMeta
import com.hieu10.mdnotes.ui.states.SortOrder
import com.hieu10.mdnotes.ui.theme.LocalSemanticColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.viewmodel.HomeViewModel

@Composable
fun HomeFragment(
    viewModel: HomeViewModel,
    onNoteClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onNewNoteClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeContent(
        state = state,
        onNoteClick = onNoteClick,
        onArchiveNote = viewModel::archiveNote,
        onTrashNote = viewModel::trashNote,
        onTogglePinNote = viewModel::togglePin,
        onToggleFavourite = viewModel::toggleFavourite,
        onSortChange = viewModel::setSortOrder,
        onSearchClick = onSearchClick,
        onNewNoteClick = onNewNoteClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    state: HomeState,
    onNoteClick: (String) -> Unit,
    onArchiveNote: (String) -> Unit,
    onTrashNote: (String) -> Unit,
    onTogglePinNote: (String) -> Unit,
    onToggleFavourite: (String) -> Unit,
    onSortChange: (SortOrder) -> Unit,
    onSearchClick: () -> Unit,
    onNewNoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val semanticColors = LocalSemanticColors.current

    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar with title and search icon
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.cd_search)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.pinnedNotes.isEmpty() && state.allNotes.isEmpty()) {
            EmptyNoteState(onCreateClick = onNewNoteClick)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Pinned notes horizontal strip
                if (state.pinnedNotes.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(id = R.string.section_pinned_notes),
                            style = MaterialTheme.typography.titleSmall,
                            color = semanticColors.hint,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(end = 16.dp)
                        ) {
                            items(items = state.pinnedNotes, key = { item: NoteWithMeta -> item.note.id }) { item ->
                                NoteCard(
                                    note = item.note,
                                    folderColor = item.folderColor,
                                    onClick = { onNoteClick(item.note.id) },
                                    modifier = Modifier.width(240.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Section header with sort
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.section_all_notes),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // All notes list
                items(items = state.allNotes, key = { item: NoteWithMeta -> item.note.id }) { item ->
                    SwipeableNoteCard(
                        note = item.note,
                        folderColor = item.folderColor,
                        onSwipeArchive = { onArchiveNote(item.note.id) },
                        onSwipeDelete = { onTrashNote(item.note.id) },
                        onClick = { onNoteClick(item.note.id) },
                        modifier = Modifier.padding(vertical = 6.dp).animateItem()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFragmentLight() {
    MDNotesTheme(darkTheme = false) {
        HomeContent(
            state = homeState,
            onNoteClick = {},
            onArchiveNote = {},
            onTrashNote = {},
            onTogglePinNote = {},
            onToggleFavourite = {},
            onSortChange = {},
            onSearchClick = {},
            onNewNoteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFragmentDark() {
    MDNotesTheme(darkTheme = true) {
        HomeContent(
            state = homeState,
            onNoteClick = {},
            onArchiveNote = {},
            onTrashNote = {},
            onTogglePinNote = {},
            onToggleFavourite = {},
            onSortChange = {},
            onSearchClick = {},
            onNewNoteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmptyFragmentLight() {
    MDNotesTheme(darkTheme = false) {
        HomeContent(
            state = homeStateEmpty,
            onNoteClick = {},
            onArchiveNote = {},
            onTrashNote = {},
            onTogglePinNote = {},
            onToggleFavourite = {},
            onSortChange = {},
            onSearchClick = {},
            onNewNoteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmptyFragmentDark() {
    MDNotesTheme(darkTheme = true) {
        HomeContent(
            state = homeStateEmpty,
            onNoteClick = {},
            onArchiveNote = {},
            onTrashNote = {},
            onTogglePinNote = {},
            onToggleFavourite = {},
            onSortChange = {},
            onSearchClick = {},
            onNewNoteClick = {}
        )
    }
}