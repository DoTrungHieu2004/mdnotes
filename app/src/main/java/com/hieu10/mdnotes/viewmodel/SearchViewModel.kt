package com.hieu10.mdnotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.mdnotes.db.repositories.NoteRepository
import com.hieu10.mdnotes.ui.states.SearchResultWithFolder
import com.hieu10.mdnotes.ui.states.SearchUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SearchViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _state = MutableStateFlow(SearchUIState())
    val state: StateFlow<SearchUIState> = _state.asStateFlow()

    // In-memory recent searches (max 5)
    private val recentSearches = mutableListOf<String>()

    init {
        // Debounce query & map to results
        _query
            .debounce(400)
            .flatMapLatest { q ->
                if (q.isBlank()) {
                    flow {
                        emit(SearchUIState(
                            query = q,
                            results = emptyList(),
                            recentSearches = recentSearches.toList(),
                            isSearching = false
                        ))
                    }
                } else {
                    flow {
                        emit(SearchUIState(
                            query = q,
                            results = emptyList(),
                            recentSearches = recentSearches.toList(),
                            isSearching = true
                        ))
                        try {
                            val results = noteRepository.searchNotesWithSnippet(q)
                            emit(SearchUIState(
                                query = q,
                                results = results.map {
                                    SearchResultWithFolder(it, null)   // folder color omitted for now
                                },
                                recentSearches = recentSearches.toList(),
                                isSearching = false
                            ))
                        } catch (e: Exception) {
                            emit(SearchUIState(
                                query = q,
                                results = emptyList(),
                                recentSearches = recentSearches.toList(),
                                isSearching = false
                            ))
                        }
                    }
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                SearchUIState()
            )
    }

    fun onQueryChange(q: String) {
        _query.value = q
    }

    fun onClearQuery() {
        _query.value = ""
    }

    fun onRecentQueryClick(q: String) {
        _query.value = q
        saveRecentSearch(q)
    }

    fun onClearRecentSearches() {
        recentSearches.clear()
        _state.update { it.copy(recentSearches = emptyList()) }
    }

    private fun saveRecentSearch(query: String) {
        if (query.isBlank()) return
        recentSearches.remove(query)
        recentSearches.add(0, query)
        if (recentSearches.size > 5) {
            recentSearches.removeAt(recentSearches.lastIndex)
        }
        _state.update { it.copy(recentSearches = recentSearches.toList()) }
    }
}