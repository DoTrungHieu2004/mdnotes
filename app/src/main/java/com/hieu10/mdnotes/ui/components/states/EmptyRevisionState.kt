package com.hieu10.mdnotes.ui.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.theme.LocalMarkdownColors
import com.hieu10.mdnotes.ui.theme.LocalSemanticColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun EmptyRevisionState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.History,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = LocalMarkdownColors.current.divider
            )
            Text(
                text = stringResource(id = R.string.revision_empty_state_title),
                style = MaterialTheme.typography.headlineSmall,
                color = LocalMarkdownColors.current.textSecondary
            )
            Text(
                text = stringResource(id = R.string.revision_empty_state_description),
                style = MaterialTheme.typography.bodyMedium,
                color = LocalSemanticColors.current.hint
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStateLight() {
    MDNotesTheme(darkTheme = false) {
        EmptyRevisionState()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStateDark() {
    MDNotesTheme(darkTheme = true) {
        EmptyRevisionState()
    }
}