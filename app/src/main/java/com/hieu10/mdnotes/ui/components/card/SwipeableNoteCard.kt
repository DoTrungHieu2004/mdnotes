package com.hieu10.mdnotes.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.db.models.Note
import com.hieu10.mdnotes.sample.data.singleNoteSample
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun SwipeableNoteCard(
    note: Note,
    folderColor: Color? = null,
    onSwipeArchive: () -> Unit,
    onSwipeDelete: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onSwipeDelete()
                    false   // Don't auto-dismiss; we handle it in ViewModel
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    onSwipeArchive()
                    false
                }
                SwipeToDismissBoxValue.Settled -> false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = {
            val direction = dismissState.targetValue
            val (icon, color) = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> Icons.Filled.Archive to MaterialTheme.colorScheme.secondaryContainer
                SwipeToDismissBoxValue.EndToStart -> Icons.Filled.Delete to MaterialTheme.colorScheme.errorContainer
                SwipeToDismissBoxValue.Settled -> null to Color.Transparent
            }
            if (icon != null) {
                Row(
                    modifier = Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp),
                    horizontalArrangement = if (direction == SwipeToDismissBoxValue.StartToEnd) {
                        Arrangement.Start
                    } else {
                        Arrangement.End
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        },
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = true,
        content = {
            NoteCard(
                note = note,
                folderColor = folderColor,
                onClick = onClick
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardLight() {
    MDNotesTheme(darkTheme = false) {
        SwipeableNoteCard(
            note = singleNoteSample,
            onSwipeArchive = {},
            onSwipeDelete = {},
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCardDark() {
    MDNotesTheme(darkTheme = true) {
        SwipeableNoteCard(
            note = singleNoteSample,
            onSwipeArchive = {},
            onSwipeDelete = {},
            onClick = {}
        )
    }
}