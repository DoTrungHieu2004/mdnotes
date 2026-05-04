package com.hieu10.mdnotes.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.db.models.Note
import com.hieu10.mdnotes.sample.data.singleNoteSample
import com.hieu10.mdnotes.ui.theme.LocalMarkdownColors
import com.hieu10.mdnotes.ui.theme.LocalSemanticColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.utils.relativeTime

@Composable
fun NoteCard(
    note: Note,
    folderColor: Color? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val markdownColors = LocalMarkdownColors.current
    val semanticColors = LocalSemanticColors.current

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (note.isPinned) {
                MaterialTheme.colorScheme.surfaceContainer
            } else {
                MaterialTheme.colorScheme.surfaceContainerLow
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        // Folder accent strip (if folderColor provided)
        if (folderColor != null) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(folderColor)
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 14.dp, bottom = 14.dp)
                .fillMaxWidth()
        ) {
            // Title row with pinned/favourite indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.title.ifBlank { stringResource(id = R.string.note_untitled) },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (note.isPinned) {
                        Icon(
                            imageVector = Icons.Filled.PushPin,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = markdownColors.heading
                        )
                    }
                    if (note.isFavourite) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = semanticColors.warning
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Content preview
            Text(
                text = note.content.ifBlank { stringResource(id = R.string.note_no_content) },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = markdownColors.textSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Metadata row: folder, tags, timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side: folder badge and/or tags (placeholder for now)
                Text(
                    text = relativeTime(note.updatedAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = semanticColors.hint
                )
                // TODO: folder name, tags
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardLight() {
    MDNotesTheme(darkTheme = false) {
        NoteCard(
            note = singleNoteSample,
            onClick = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewCardDark() {
    MDNotesTheme(darkTheme = true) {
        NoteCard(
            note = singleNoteSample,
            onClick = {}
        )
    }
}