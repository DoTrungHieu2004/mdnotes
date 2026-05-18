package com.hieu10.mdnotes.sample.states

import com.hieu10.mdnotes.sample.data.revisionHistorySample
import com.hieu10.mdnotes.ui.states.RevisionHistoryUIState

val sampleRevisionHistoryState = RevisionHistoryUIState(
    revisions = revisionHistorySample,
    expandedRevisionId = null,
    isLoading = false
)

val sampleRevisionHistoryLoadingState = RevisionHistoryUIState(
    revisions = emptyList(),
    expandedRevisionId = null,
    isLoading = true
)

val sampleRevisionHistoryEmptyState = RevisionHistoryUIState(
    revisions = emptyList(),
    expandedRevisionId = null,
    isLoading = false
)