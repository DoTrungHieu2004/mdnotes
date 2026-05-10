package com.hieu10.mdnotes.ui.states

import androidx.compose.ui.graphics.Color
import com.hieu10.mdnotes.db.pojo.SearchResult

data class SearchUIState(
    val query: String = "",
    val results: List<SearchResultWithFolder> = emptyList(),
    val recentSearches: List<String> = emptyList(),
    val isSearching: Boolean = false      // true while a debounced query is in-flight
)

data class SearchResultWithFolder(
    val searchResult: SearchResult,
    val folderColor: Color? = null
)