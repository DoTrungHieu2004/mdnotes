package com.hieu10.mdnotes.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.sample.states.searchEmptyState
import com.hieu10.mdnotes.sample.states.searchNoResultsState
import com.hieu10.mdnotes.sample.states.searchState
import com.hieu10.mdnotes.ui.components.bar.SearchTopBar
import com.hieu10.mdnotes.ui.components.card.SearchResultCard
import com.hieu10.mdnotes.ui.components.section.RecentSearchesSection
import com.hieu10.mdnotes.ui.components.states.EmptySearchResultState
import com.hieu10.mdnotes.ui.components.states.EmptySearchState
import com.hieu10.mdnotes.ui.states.SearchUIState
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onNoteClick: (String) -> Unit
) {

}

@Composable
private fun SearchContent(
    state: SearchUIState,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onResultClick: (String) -> Unit,       // noteId
    onBack: () -> Unit,
    onRecentQueryClick: (String) -> Unit,
    onClearRecentSearches: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                query = state.query,
                onQueryChange = onQueryChange,
                onClearQuery = onClearQuery,
                onBack = onBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            // Recent searches (only when query empty and available)
            if (state.query.isEmpty() && state.recentSearches.isNotEmpty()) {
                RecentSearchesSection(
                    recentQueries = state.recentSearches,
                    onQueryClick = onRecentQueryClick,
                    onClearAll = onClearRecentSearches
                )
            }

            // Loading indicator
            if (state.isSearching) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            // No results
            else if (state.query.isNotEmpty() && state.results.isEmpty()) {
                EmptySearchResultState()
            }
            // Results list
            else if (state.results.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(state.results, key = { it.searchResult.note.id }) { item ->
                        SearchResultCard(
                            note = item.searchResult.note,
                            snippet = item.searchResult.snippet,
                            folderColor = item.folderColor,
                            onClick = { onResultClick(item.searchResult.note.id) }
                        )
                    }
                }
            }
            // Empty/initial state (no query, no recent searches)
            else if (state.query.isEmpty() && state.recentSearches.isEmpty()) {
                EmptySearchState()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    MDNotesTheme(darkTheme = false) {
        SearchContent(
            state = searchState,
            onQueryChange = {},
            onClearQuery = {},
            onResultClick = {},
            onBack = {},
            onRecentQueryClick = {},
            onClearRecentSearches = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    MDNotesTheme(darkTheme = true) {
        SearchContent(
            state = searchState,
            onQueryChange = {},
            onClearQuery = {},
            onResultClick = {},
            onBack = {},
            onRecentQueryClick = {},
            onClearRecentSearches = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenNoResultLight() {
    MDNotesTheme(darkTheme = false) {
        SearchContent(
            state = searchNoResultsState,
            onQueryChange = {},
            onClearQuery = {},
            onResultClick = {},
            onBack = {},
            onRecentQueryClick = {},
            onClearRecentSearches = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenNoResultDark() {
    MDNotesTheme(darkTheme = true) {
        SearchContent(
            state = searchNoResultsState,
            onQueryChange = {},
            onClearQuery = {},
            onResultClick = {},
            onBack = {},
            onRecentQueryClick = {},
            onClearRecentSearches = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenEmptyLight() {
    MDNotesTheme(darkTheme = false) {
        SearchContent(
            state = searchEmptyState,
            onQueryChange = {},
            onClearQuery = {},
            onResultClick = {},
            onBack = {},
            onRecentQueryClick = {},
            onClearRecentSearches = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenEmptyDark() {
    MDNotesTheme(darkTheme = true) {
        SearchContent(
            state = searchEmptyState,
            onQueryChange = {},
            onClearQuery = {},
            onResultClick = {},
            onBack = {},
            onRecentQueryClick = {},
            onClearRecentSearches = {}
        )
    }
}