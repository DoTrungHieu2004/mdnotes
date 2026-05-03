package com.hieu10.mdnotes.ui.components.input

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.ui.theme.SerifFont

@Composable
fun NoteTitleField(
    title: String,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row {
        BasicTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = modifier.fillMaxWidth().padding(horizontal = 4.dp).weight(1f),
            textStyle = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = SerifFont,
                color = MaterialTheme.colorScheme.onSurface
            ),
            singleLine = false,
            maxLines = 2,
            decorationBox = { innerTextField ->
                Box {
                    if (title.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.hint_note_title),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = SerifFont,
                                color = Color.Gray.copy(alpha = 0.5f)
                            )
                        )
                    }
                    innerTextField()
                }
            },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFieldLight() {
    MDNotesTheme(darkTheme = false) {
        NoteTitleField(
            title = "",
            onTitleChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFieldDark() {
    MDNotesTheme(darkTheme = true) {
        NoteTitleField(
            title = "",
            onTitleChange = {}
        )
    }
}