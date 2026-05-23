package com.hieu10.mdnotes.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import androidx.core.graphics.toColorInt

@Composable
fun CreateFolderDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, colorHex: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedColorHex by remember { mutableStateOf("#4F46E5") }   // default
    var nameError by remember { mutableStateOf<String?>(null) }

    val predefinedColors = listOf(
        "#4F46E5", "#14B8A6", "#F59E0B", "#DC2626",
        "#8B5CF6", "#EC4899", "#06B6D4", "#84CC16",
        "#F97316", "#6366F1"
    )

    val nameValidationString = stringResource(id = R.string.validation_name_empty)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.dialog_create_folder)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it; nameError = null },
                    label = { Text(text = stringResource(id = R.string.hint_folder_name)) },
                    supportingText = nameError?.let { { Text(text = it) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Color picker grid
                Text(
                    text = stringResource(id = R.string.hint_color),
                    style = MaterialTheme.typography.labelLarge
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    predefinedColors.forEach { hex ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(color = Color(hex.toColorInt()))
                                .border(
                                    width = if (hex == selectedColorHex) 3.dp else 0.dp,
                                    color = if (hex == selectedColorHex) MaterialTheme.colorScheme.onSurface else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable { selectedColorHex = hex }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isBlank()) {
                        nameError = nameValidationString
                        return@TextButton
                    }
                    onConfirm(name.trim(), selectedColorHex)
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
        CreateFolderDialog(
            onDismiss = {},
            onConfirm = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDialogDark() {
    MDNotesTheme(darkTheme = true) {
        CreateFolderDialog(
            onDismiss = {},
            onConfirm = { _, _ -> }
        )
    }
}