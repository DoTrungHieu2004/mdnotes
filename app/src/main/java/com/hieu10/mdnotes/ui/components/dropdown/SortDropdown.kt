package com.hieu10.mdnotes.ui.components.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.states.SortOrder
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun SortDropdown(
    currentSort: SortOrder,
    onSortChange: (SortOrder) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text(
                text = when (currentSort) {
                    SortOrder.LAST_MODIFIED -> stringResource(id = R.string.dropdown_sort_last_modified)
                    SortOrder.CREATED_DATE -> stringResource(id = R.string.dropdown_sort_created_date)
                    SortOrder.TITLE_AZ -> stringResource(id = R.string.dropdown_sort_ascending)
                    SortOrder.TITLE_ZA -> stringResource(id = R.string.dropdown_sort_descending)
                },
                style = MaterialTheme.typography.labelLarge
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            SortOrder.entries.forEach { order ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = order.displayName())) },
                    onClick = { onSortChange(order); expanded = false },
                    leadingIcon = if (order == currentSort) {
                        { Icon(imageVector = Icons.Filled.Check, contentDescription = null) }
                    } else null
                )
            }
        }
    }
}

fun SortOrder.displayName(): Int = when (this) {
    SortOrder.LAST_MODIFIED -> R.string.dropdown_sort_last_modified
    SortOrder.CREATED_DATE -> R.string.dropdown_sort_created_date
    SortOrder.TITLE_AZ -> R.string.dropdown_sort_ascending
    SortOrder.TITLE_ZA -> R.string.dropdown_sort_descending
}

@Preview(showBackground = true)
@Composable
private fun PreviewDropdownLight() {
    MDNotesTheme(darkTheme = false) {
        SortDropdown(
            currentSort = SortOrder.LAST_MODIFIED,
            onSortChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDropdownDark() {
    MDNotesTheme(darkTheme = true) {
        SortDropdown(
            currentSort = SortOrder.LAST_MODIFIED,
            onSortChange = {}
        )
    }
}