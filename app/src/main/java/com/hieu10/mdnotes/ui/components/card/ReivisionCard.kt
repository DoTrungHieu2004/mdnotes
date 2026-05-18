package com.hieu10.mdnotes.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.db.models.NoteRevision
import com.hieu10.mdnotes.sample.data.singleNoteRevisionSample
import com.hieu10.mdnotes.ui.theme.LocalMarkdownColors
import com.hieu10.mdnotes.ui.theme.LocalSemanticColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.ui.theme.MonoFont
import com.hieu10.mdnotes.utils.formatRevisionTimestamp

@Composable
fun RevisionCard(
    revision: NoteRevision,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onRestore: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header row: date/time + description, always visible
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = formatRevisionTimestamp(revision.revisionTimestamp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = LocalSemanticColors.current.info
                    )
                    Text(
                        text = revision.revisionDescription ?: stringResource(id = R.string.auto_save_note),
                        style = MaterialTheme.typography.labelSmall,
                        color = LocalSemanticColors.current.hint
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = stringResource(id = R.string.cd_toggle),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Expanded content
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = LocalMarkdownColors.current.divider)
                Spacer(modifier = Modifier.height(8.dp))

                // Preview text (truncated to ~200 chars, or full if short)
                Text(
                    text = revision.contentSnapshot.take(300) + if (revision.contentSnapshot.length > 300) "…" else "",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = MonoFont,
                        lineHeight = 20.sp
                    ),
                    color = LocalMarkdownColors.current.textSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onRestore) {
                        Icon(
                            imageVector = Icons.Filled.Restore,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(id = R.string.btn_restore))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = onDelete,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(id = R.string.btn_delete))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardLight() {
    MDNotesTheme(darkTheme = false) {
        RevisionCard(
            revision = singleNoteRevisionSample,
            isExpanded = true,
            onToggleExpand = {},
            onRestore = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardDark() {
    MDNotesTheme(darkTheme = true) {
        RevisionCard(
            revision = singleNoteRevisionSample,
            isExpanded = true,
            onToggleExpand = {},
            onRestore = {},
            onDelete = {}
        )
    }
}