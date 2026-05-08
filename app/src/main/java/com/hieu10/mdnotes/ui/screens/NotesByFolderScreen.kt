package com.hieu10.mdnotes.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.sample.states.notesByFolderState
import com.hieu10.mdnotes.sample.states.notesByFolderEmptyState
import com.hieu10.mdnotes.ui.components.card.NoteCard
import com.hieu10.mdnotes.ui.components.card.SwipeableNoteCard
import com.hieu10.mdnotes.ui.components.dropdown.SortDropdown
import com.hieu10.mdnotes.ui.components.states.EmptyNoteState
import com.hieu10.mdnotes.ui.states.NotesByFolderUIState
import com.hieu10.mdnotes.ui.states.SortOrder
import com.hieu10.mdnotes.ui.theme.LocalSemanticColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun NotesByFolderScreen(
    folderId: String,
    onBack: () -> Unit,
    onNoteClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onNewNoteClick: () -> Unit
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotesByFolderContent(
    state: NotesByFolderUIState,
    onBack: () -> Unit,
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = state.folderName)
                        Text(
                            text = stringResource(id = R.string.folder_note_count, state.allNotes.size + state.pinnedNotes.size),
                            style = MaterialTheme.typography.labelSmall,
                            color = LocalSemanticColors.current.hint
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.cd_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                        .let { base ->
                            // subtle folder color overlay
                            state.folderColor?.let { folderCol ->
                                base.compositeOver(folderCol.copy(alpha = 0.05f))
                            } ?: base
                        }
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.pinnedNotes.isEmpty() && state.allNotes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                EmptyNoteState(onCreateClick = onNewNoteClick)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Pinned strip
                if (state.pinnedNotes.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(id = R.string.section_pinned_notes),
                            style = MaterialTheme.typography.titleSmall,
                            color = LocalSemanticColors.current.hint,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(items = state.pinnedNotes, key = { it.note.id} ) { item ->
                                NoteCard(
                                    note = item.note,
                                    folderColor = state.folderColor,
                                    onClick = { onNoteClick(item.note.id) },
                                    modifier = Modifier.width(240.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Section header & sort
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
                        SortDropdown(
                            currentSort = state.sortOrder,
                            onSortChange = onSortChange
                        )
                    }
                }

                // All notes
                items(items = state.allNotes, key = { it.note.id }) { item ->
                    SwipeableNoteCard(
                        note = item.note,
                        folderColor = state.folderColor,
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
private fun PreviewScreenLight() {
    MDNotesTheme(darkTheme = false) {
        NotesByFolderContent(
            state = notesByFolderState,
            onBack = {},
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
private fun PreviewScreenDark() {
    MDNotesTheme(darkTheme = true) {
        NotesByFolderContent(
            state = notesByFolderState,
            onBack = {},
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
private fun PreviewEmptyScreenLight() {
    MDNotesTheme(darkTheme = false) {
        NotesByFolderContent(
            state = notesByFolderEmptyState,
            onBack = {},
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
private fun PreviewEmptyScreenDark() {
    MDNotesTheme(darkTheme = true) {
        NotesByFolderContent(
            state = notesByFolderEmptyState,
            onBack = {},
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