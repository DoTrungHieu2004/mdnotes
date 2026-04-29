package com.hieu10.mdnotes.ui.screens.placeholders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    MDNotesTheme(darkTheme = false) {
        PlaceholderScreen(title = "Placeholder")
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    MDNotesTheme(darkTheme = true) {
        PlaceholderScreen(title = "Placeholder")
    }
}