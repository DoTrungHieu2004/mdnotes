package com.hieu10.mdnotes.ui.components.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun NoteMetadataBar(
    tags: List<String>,
    folderName: String?,
    backlinkCount: Int,
    onTagsClick: () -> Unit,
    onFolderClick: () -> Unit,
    onLinksClick: () -> Unit,
    onAddReminder: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tagColor = MaterialTheme.colorScheme.secondaryContainer
    val onTagColor = MaterialTheme.colorScheme.onSecondaryContainer

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tags chip
        if (tags.isNotEmpty()) {
            AssistChip(
                onClick = onTagsClick,
                label = { Text(text = tags.take(3).joinToString(", ")) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Label,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = tagColor,
                    labelColor = onTagColor,
                    leadingIconContentColor = onTagColor
                )
            )
        }

        // Folder chip
        AssistChip(
            onClick = onFolderClick,
            label = { Text(text = folderName ?: stringResource(id = R.string.note_no_folder)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Folder,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = AssistChipDefaults.assistChipColors(
                containerColor = tagColor,
                labelColor = onTagColor,
                leadingIconContentColor = onTagColor
            )
        )

        // Links pill
        AssistChip(
            onClick = onLinksClick,
            label = { Text(text = stringResource(id = R.string.note_link_count, backlinkCount)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Link,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = AssistChipDefaults.assistChipColors(
                containerColor = tagColor,
                labelColor = onTagColor,
                leadingIconContentColor = onTagColor
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // Reminder button
        IconButton(onClick = onAddReminder) {
            Icon(
                imageVector = Icons.Filled.Alarm,
                contentDescription = stringResource(id = R.string.cd_add_reminder),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBarLight() {
    MDNotesTheme(darkTheme = false) {
        NoteMetadataBar(
            tags = listOf("Tag1", "Tag2"),
            folderName = "Folder",
            backlinkCount = 1,
            onTagsClick = {},
            onFolderClick = {},
            onLinksClick = {},
            onAddReminder = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBarDark() {
    MDNotesTheme(darkTheme = true) {
        NoteMetadataBar(
            tags = listOf("Tag1", "Tag2"),
            folderName = "Folder",
            backlinkCount = 1,
            onTagsClick = {},
            onFolderClick = {},
            onLinksClick = {},
            onAddReminder = {}
        )
    }
}