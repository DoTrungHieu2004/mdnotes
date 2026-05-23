package com.hieu10.mdnotes.ui.components.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun CreateTagDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    val tagValidationString = stringResource(id = R.string.validation_name_empty)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.dialog_create_tag)) },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it; nameError = null },
                label = { Text(text = stringResource(id = R.string.hint_tag_name)) },
                isError = nameError != null,
                supportingText = nameError?.let { { Text(text = it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isBlank()) {
                        nameError = tagValidationString
                        return@TextButton
                    }
                    onConfirm(name.trim())
                }
            ) {
                Text(text = stringResource(id = R.string.btn_create))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.btn_cancel))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewDialogLight() {
    MDNotesTheme(darkTheme = false) {
        CreateTagDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDialogDark() {
    MDNotesTheme(darkTheme = true) {
        CreateTagDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}