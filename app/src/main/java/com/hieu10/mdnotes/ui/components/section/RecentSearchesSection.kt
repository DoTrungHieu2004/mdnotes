package com.hieu10.mdnotes.ui.components.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.theme.LocalSemanticColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun RecentSearchesSection(
    recentQueries: List<String>,
    onQueryClick: (String) -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (recentQueries.isEmpty()) return
    Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.section_recent_searches),
                style = MaterialTheme.typography.labelLarge,
                color = LocalSemanticColors.current.hint
            )
            TextButton(onClick = onClearAll) {
                Text(
                    text = stringResource(id = R.string.btn_clear_all),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        Spacer(modifier = Modifier.heightIn(4.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            recentQueries.forEach { query ->
                InputChip(
                    selected = false,
                    onClick = { onQueryClick(query) },
                    label = { Text(text = query) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.NorthWest,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSectionLight() {
    MDNotesTheme(darkTheme = false) {
        RecentSearchesSection(
            recentQueries = listOf("Query 1", "Query 2", "Query 3"),
            onQueryClick = {},
            onClearAll = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSectionDark() {
    MDNotesTheme(darkTheme = true) {
        RecentSearchesSection(
            recentQueries = listOf("Query 1", "Query 2", "Query 3"),
            onQueryClick = {},
            onClearAll = {}
        )
    }
}