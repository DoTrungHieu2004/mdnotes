package com.hieu10.mdnotes.ui.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.sample.states.notesByTagEmptyState
import com.hieu10.mdnotes.ui.states.NotesByTagUIState
import com.hieu10.mdnotes.ui.theme.LocalMarkdownColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun EmptyNotesWithTagState(
    state: NotesByTagUIState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val markdownColors = LocalMarkdownColors.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Label,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = markdownColors.divider
            )
            Text(
                text = stringResource(id = R.string.note_tag_empty_state_title, state.tagName),
                style = MaterialTheme.typography.headlineSmall,
                color = markdownColors.textSecondary
            )
            Text(
                text = stringResource(id = R.string.note_tag_empty_state_description),
                style = MaterialTheme.typography.bodyMedium,
                color = markdownColors.textSecondary,
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = TextAlign.Center
            )
            Button(onClick = onClick) {
                Text(text = stringResource(id = R.string.btn_browse_all_notes))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStateLight() {
    MDNotesTheme(darkTheme = false) {
        EmptyNotesWithTagState(
            state = notesByTagEmptyState,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStateDark() {
    MDNotesTheme(darkTheme = true) {
        EmptyNotesWithTagState(
            state = notesByTagEmptyState,
            onClick = {}
        )
    }
}