package com.hieu10.mdnotes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.db.models.NoteRevision
import com.hieu10.mdnotes.sample.states.sampleRevisionHistoryEmptyState
import com.hieu10.mdnotes.sample.states.sampleRevisionHistoryLoadingState
import com.hieu10.mdnotes.sample.states.sampleRevisionHistoryState
import com.hieu10.mdnotes.ui.components.card.RevisionCard
import com.hieu10.mdnotes.ui.components.states.EmptyRevisionState
import com.hieu10.mdnotes.ui.states.RevisionHistoryUIState
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun RevisionHistoryScreen(
    noteId: String,
    onBack: () -> Unit
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RevisionHistoryContent(
    state: RevisionHistoryUIState,
    onBack: () -> Unit,
    onToggleExpand: (String) -> Unit,
    onRestore: (NoteRevision) -> Unit,
    onDelete: (NoteRevision) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_bar_revision_history)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.cd_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.revisions.isEmpty() -> {
                    EmptyRevisionState()
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(state.revisions, key = { it.revisionId }) { revision ->
                            RevisionCard(
                                revision = revision,
                                isExpanded = state.expandedRevisionId == revision.revisionId,
                                onToggleExpand = { onToggleExpand(revision.revisionId) },
                                onRestore = { onRestore(revision) },
                                onDelete = { onDelete(revision) }
                            )
                        }
                    }
                }
            }

            // Loading overlay when restoring
            if (state.isRestoring) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    MDNotesTheme(darkTheme = false) {
        RevisionHistoryContent(
            state = sampleRevisionHistoryState,
            onBack = {},
            onToggleExpand = {},
            onRestore = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    MDNotesTheme(darkTheme = true) {
        RevisionHistoryContent(
            state = sampleRevisionHistoryState,
            onBack = {},
            onToggleExpand = {},
            onRestore = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLoadingLight() {
    MDNotesTheme(darkTheme = false) {
        RevisionHistoryContent(
            state = sampleRevisionHistoryLoadingState,
            onBack = {},
            onToggleExpand = {},
            onRestore = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLoadingDark() {
    MDNotesTheme(darkTheme = true) {
        RevisionHistoryContent(
            state = sampleRevisionHistoryLoadingState,
            onBack = {},
            onToggleExpand = {},
            onRestore = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenEmptyLight() {
    MDNotesTheme(darkTheme = false) {
        RevisionHistoryContent(
            state = sampleRevisionHistoryEmptyState,
            onBack = {},
            onToggleExpand = {},
            onRestore = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenEmptyDark() {
    MDNotesTheme(darkTheme = true) {
        RevisionHistoryContent(
            state = sampleRevisionHistoryEmptyState,
            onBack = {},
            onToggleExpand = {},
            onRestore = {},
            onDelete = {}
        )
    }
}