package com.hieu10.mdnotes.sample.states

import androidx.compose.ui.graphics.Color
import com.hieu10.mdnotes.db.models.Note
import com.hieu10.mdnotes.db.pojo.SearchResult
import com.hieu10.mdnotes.ui.states.SearchResultWithFolder
import com.hieu10.mdnotes.ui.states.SearchUIState

val searchState = SearchUIState(
    query = "",
    results = listOf(
        SearchResultWithFolder(
            searchResult = SearchResult(
                note = Note(
                    id = "sample-002",
                    title = "Markdown Syntax Guide",
                    content = "# Headings\n## Subheadings\n**Bold text**\n*Italic text*\n- List items\n[Links](url)\n\nUse triple backticks for code blocks.",
                    createdAt = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000,
                    updatedAt = System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000,
                    isPinned = true,
                    isFavourite = false,
                    wordCount = 22
                ),
                snippet = "# <b>Markdown</b> Syntax Guide ## Subheadings **Bold text** *Italic text* - List items...",
            ),
            folderColor = null
        ),
        SearchResultWithFolder(
            searchResult = SearchResult(
                note = Note(
                    id = "sample-004",
                    title = "Project Ideas",
                    content = "Note-taking app with Kotlin and Jetpack Compose. Features: Markdown support, FTS search, tags, folders, cross-note linking, and revision history.",
                    createdAt = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000,
                    updatedAt = System.currentTimeMillis() - 2 * 60 * 60 * 1000,
                    isPinned = true,
                    isFavourite = false,
                    wordCount = 24
                ),
                snippet = "Note-taking app with Kotlin and Jetpack Compose. Features: <b>Markdown</b> support, FTS search, tags...",
            ),
            folderColor = Color(0xFF14B8A6)
        ),
        SearchResultWithFolder(
            searchResult = SearchResult(
                note = Note(
                    id = "sample-007",
                    title = "Code Snippets — Kotlin",
                    content = "// Extension function for formatting dates\nfun Long.toReadableDate(): String {\n    val sdf = SimpleDateFormat(\"MMM dd, yyyy\", Locale.getDefault())\n    return sdf.format(Date(this))\n}",
                    createdAt = System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000,
                    updatedAt = System.currentTimeMillis() - 4 * 24 * 60 * 60 * 1000,
                    isPinned = true,
                    isFavourite = true,
                    wordCount = 35
                ),
                snippet = "<b>Markdown</b> rendering in Kotlin using Compose. Code snippets and formatting examples...",
            ),
            folderColor = Color(0xFF4F46E5)
        )
    ),
    recentSearches = listOf("compose", "room database", "kotlin", "project", "weekly meeting"),
    isSearching = false
)

val searchEmptyState = SearchUIState(
    query = "",
    results = emptyList(),
    recentSearches = emptyList(),
    isSearching = false
)

val searchNoResultsState = SearchUIState(
    query = "flutter",
    results = emptyList(),
    recentSearches = emptyList(),
    isSearching = false
)