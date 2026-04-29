package com.hieu10.mdnotes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun CreateActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonLight() {
    MDNotesTheme(darkTheme = false) {
        CreateActionButton(
            icon = Icons.Default.Create,
            label = stringResource(id = R.string.cd_create),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonDark() {
    MDNotesTheme(darkTheme = true) {
        CreateActionButton(
            icon = Icons.Default.Create,
            label = stringResource(id = R.string.cd_create),
            onClick = {}
        )
    }
}