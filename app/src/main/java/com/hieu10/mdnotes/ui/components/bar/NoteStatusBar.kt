package com.hieu10.mdnotes.ui.components.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.theme.LocalSemanticColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun NoteStatusBar(
    saveStatus: String,
    wordCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = saveStatus,
            style = MaterialTheme.typography.labelSmall,
            color = LocalSemanticColors.current.hint
        )
        Text(
            text = stringResource(id = R.string.note_word_count, wordCount),
            style = MaterialTheme.typography.labelSmall,
            color = LocalSemanticColors.current.hint
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStatusBarLight() {
    MDNotesTheme(darkTheme = false) {
        NoteStatusBar(
            saveStatus = "Saved",
            wordCount = 100
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStatusBarDark() {
    MDNotesTheme(darkTheme = true) {
        NoteStatusBar(
            saveStatus = "Saved",
            wordCount = 100
        )
    }
}