package com.hieu10.mdnotes.ui.components.button

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun FormatButton(
    icon: ImageVector,
    contentDesc: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(40.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDesc,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonLight() {
    MDNotesTheme(darkTheme = false) {
        FormatButton(
            icon = Icons.Filled.SmartButton,
            contentDesc = "Button",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonDark() {
    MDNotesTheme(darkTheme = true) {
        FormatButton(
            icon = Icons.Filled.SmartButton,
            contentDesc = "Button",
            onClick = {}
        )
    }
}