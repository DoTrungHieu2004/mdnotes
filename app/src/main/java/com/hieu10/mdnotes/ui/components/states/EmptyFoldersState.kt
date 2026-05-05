package com.hieu10.mdnotes.ui.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreateNewFolder
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
import com.hieu10.mdnotes.ui.theme.LocalMarkdownColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun EmptyFoldersState(
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.CreateNewFolder,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = LocalMarkdownColors.current.divider
            )
            Text(
                text = stringResource(id = R.string.folder_empty_state_title),
                style = MaterialTheme.typography.headlineSmall,
                color = LocalMarkdownColors.current.textSecondary
            )
            Text(
                text = stringResource(id = R.string.folder_empty_state_description),
                style = MaterialTheme.typography.bodyMedium,
                color = LocalMarkdownColors.current.textSecondary,
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = TextAlign.Center
            )
            Button(onClick = onCreateClick) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.btn_create_folder))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStateLight() {
    MDNotesTheme(darkTheme = false) {
        EmptyFoldersState(onCreateClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStateDark() {
    MDNotesTheme(darkTheme = true) {
        EmptyFoldersState(onCreateClick = {})
    }
}