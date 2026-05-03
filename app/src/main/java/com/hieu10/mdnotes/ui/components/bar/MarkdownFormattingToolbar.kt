package com.hieu10.mdnotes.ui.components.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatStrikethrough
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.InsertLink
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.components.button.FormatButton
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun MarkdownFormattingToolbar(
    onBold: () -> Unit,
    onItalic: () -> Unit,
    onHeading: () -> Unit,
    onStrikethrough: () -> Unit,
    onBulletList: () -> Unit,
    onLink: () -> Unit,
    onImage: () -> Unit,
    onAttachment: () -> Unit,
    onCodeBlock: () -> Unit,
    onPreviewToggle: () -> Unit,
    isPreview: Boolean,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        item {
            FormatButton(
                icon = Icons.Filled.FormatBold,
                contentDesc = stringResource(id = R.string.cd_bold),
                onClick = onBold
            )
        }
        item {
            FormatButton(
                icon = Icons.Filled.FormatItalic,
                contentDesc = stringResource(id = R.string.cd_italic),
                onClick = onItalic
            )
        }
        item {
            FormatButton(
                icon = Icons.Filled.FormatSize,
                contentDesc = stringResource(id = R.string.cd_heading),
                onClick = onHeading
            )
        }
        item {
            FormatButton(
                icon = Icons.Filled.FormatStrikethrough,
                contentDesc = stringResource(id = R.string.cd_strikethrough),
                onClick = onStrikethrough
            )
        }
        item {
            FormatButton(
                icon = Icons.AutoMirrored.Filled.FormatListBulleted,
                contentDesc = stringResource(id = R.string.cd_list),
                onClick = onBulletList
            )
        }
        item {
            FormatButton(
                icon = Icons.Filled.InsertLink,
                contentDesc = stringResource(id = R.string.cd_link),
                onClick = onLink
            )
        }
        item {
            FormatButton(
                icon = Icons.Filled.Image,
                contentDesc = stringResource(id = R.string.cd_image),
                onClick = onImage
            )
        }
        item {
            FormatButton(
                icon = Icons.Filled.AttachFile,
                contentDesc = stringResource(id = R.string.cd_attachment),
                onClick = onAttachment
            )
        }
        item {
            FormatButton(
                icon = Icons.Filled.Code,
                contentDesc = stringResource(id = R.string.cd_code),
                onClick = onCodeBlock
            )
        }
        item {
            FormatButton(
                icon = if (isPreview) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                contentDesc = stringResource(id = R.string.cd_preview),
                onClick = onPreviewToggle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewToolbarLight() {
    MDNotesTheme(darkTheme = false) {
        MarkdownFormattingToolbar(
            onBold = {},
            onItalic = {},
            onHeading = {},
            onStrikethrough = {},
            onBulletList = {},
            onLink = {},
            onImage = {},
            onAttachment = {},
            onCodeBlock = {},
            onPreviewToggle = {},
            isPreview = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewToolbarDark() {
    MDNotesTheme(darkTheme = true) {
        MarkdownFormattingToolbar(
            onBold = {},
            onItalic = {},
            onHeading = {},
            onStrikethrough = {},
            onBulletList = {},
            onLink = {},
            onImage = {},
            onAttachment = {},
            onCodeBlock = {},
            onPreviewToggle = {},
            isPreview = false
        )
    }
}