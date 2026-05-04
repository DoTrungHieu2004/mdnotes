package com.hieu10.mdnotes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.sample.states.noteEditorState
import com.hieu10.mdnotes.ui.components.MarkdownPreview
import com.hieu10.mdnotes.ui.components.bar.MarkdownFormattingToolbar
import com.hieu10.mdnotes.ui.components.bar.NoteMetadataBar
import com.hieu10.mdnotes.ui.components.bar.NoteStatusBar
import com.hieu10.mdnotes.ui.components.input.NoteTitleField
import com.hieu10.mdnotes.ui.states.NoteEditorUIState
import com.hieu10.mdnotes.ui.theme.LocalMarkdownColors
import com.hieu10.mdnotes.ui.theme.LocalMarkdownTypography
import com.hieu10.mdnotes.ui.theme.LocalSemanticColors
import com.hieu10.mdnotes.ui.theme.MDNotesTheme
import com.hieu10.mdnotes.viewmodel.NoteEditorViewModel

@Composable
fun NoteEditorScreen(
    noteId: String,
    viewModel: NoteEditorViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NoteEditorContent(
        state = state,
        onBack = {
            // auto-save triggered by ViewModel before navigating back
            viewModel.autoSave()
            onBack()
        },
        onTitleChange = viewModel::onTitleChanged,
        onContentChange = viewModel::onContentChanged,
        onTogglePin = viewModel::togglePin,
        onToggleFavourite = viewModel::toggleFavourite,
        onToggleLocked = viewModel::toggleLocked,
        onOverflowAction = viewModel::onOverflowAction,
        onBold = viewModel::insertBold,
        onItalic = viewModel::insertItalic,
        onHeading = viewModel::insertHeading,
        onStrikethrough = viewModel::insertStrikethrough,
        onBulletList = viewModel::insertBulletList,
        onLink = viewModel::insertLink,
        onImage = viewModel::insertImage,
        onAttachment = viewModel::insertAttachment,
        onCodeBlock = viewModel::insertCodeBlock,
        onTogglePreview = viewModel::togglePreview,
        onTagsClick = { /* navigate to tag picker */ },
        onFolderClick = { /* navigate to folder picker */ },
        onLinksClick = { /* navigate to links screen */ },
        onAddReminder = { /* open reminder dialog */ }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteEditorContent(
    state: NoteEditorUIState,
    onBack: () -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onTogglePin: () -> Unit,
    onToggleFavourite: () -> Unit,
    onToggleLocked: () -> Unit,
    onOverflowAction: (String) -> Unit,
    // Formatting toolbar
    onBold: () -> Unit,
    onItalic: () -> Unit,
    onHeading: () -> Unit,
    onStrikethrough: () -> Unit,
    onBulletList: () -> Unit,
    onLink: () -> Unit,
    onImage: () -> Unit,
    onAttachment: () -> Unit,
    onCodeBlock: () -> Unit,
    onTogglePreview: () -> Unit,
    // Metadata bar
    onTagsClick: () -> Unit,
    onFolderClick: () -> Unit,
    onLinksClick: () -> Unit,
    onAddReminder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    NoteTitleField(
                        title = state.title,
                        onTitleChange = onTitleChange
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.cd_back)
                        )
                    }
                },
                actions = {
                    if (state.isFavourite) {
                        IconButton(onClick = onToggleFavourite) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = stringResource(id = R.string.cd_unfavourite)
                            )
                        }
                    } else {
                        IconButton(onClick = onToggleFavourite) {
                            Icon(
                                imageVector = Icons.Filled.StarOutline,
                                contentDescription = stringResource(id = R.string.cd_favourite)
                            )
                        }
                    }
                    if (state.isLocked) {
                        IconButton(onClick = onToggleLocked) {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = stringResource(id = R.string.cd_unlock)
                            )
                        }
                    } else {
                        IconButton(onClick = onToggleLocked) {
                            Icon(
                                imageVector = Icons.Filled.LockOpen,
                                contentDescription = stringResource(id = R.string.cd_lock)
                            )
                        }
                    }

                    var overflowExpanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { overflowExpanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = stringResource(id = R.string.cd_more)
                        )
                    }
                    DropdownMenu(
                        expanded = overflowExpanded,
                        onDismissRequest = { overflowExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.dropdown_revision_history)) },
                            onClick = { onOverflowAction("revisions"); overflowExpanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.dropdown_note_info)) },
                            onClick = { onOverflowAction("info"); overflowExpanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.dropdown_archive)) },
                            onClick = { onOverflowAction("archive"); overflowExpanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.dropdown_move_to_trash)) },
                            onClick = { onOverflowAction("trash"); overflowExpanded = false }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Column {
                NoteStatusBar(
                    saveStatus = stringResource(id = state.saveStatus),
                    wordCount = state.wordCount
                )
                NoteMetadataBar(
                    tags = state.tags,
                    folderName = state.folderName,
                    backlinkCount = state.backlinkCount,
                    onTagsClick = onTagsClick,
                    onFolderClick = onFolderClick,
                    onLinksClick = onLinksClick,
                    onAddReminder = onAddReminder
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            MarkdownFormattingToolbar(
                onBold = onBold,
                onItalic = onItalic,
                onHeading = onHeading,
                onStrikethrough = onStrikethrough,
                onBulletList = onBulletList,
                onLink = onLink,
                onImage = onImage,
                onAttachment = onAttachment,
                onCodeBlock = onCodeBlock,
                onPreviewToggle = onTogglePreview,
                isPreview = state.isPreview
            )
            
            if (state.isPreview) {
                MarkdownPreview(
                    content = state.content,
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                )
            } else {
                BasicTextField(
                    value = state.content,
                    onValueChange = onContentChange,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    textStyle = LocalMarkdownTypography.current.body.copy(
                        color = LocalMarkdownColors.current.textPrimary
                    ),
                    decorationBox = { innerTextField ->
                        if (state.content.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.hint_note_content),
                                style = LocalMarkdownTypography.current.body.copy(
                                    color = LocalSemanticColors.current.hint
                                )
                            )
                        }
                        innerTextField()
                    },
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    MDNotesTheme(darkTheme = false) {
        NoteEditorContent(
            state = noteEditorState, onBack = {}, onTitleChange = {}, onContentChange = {},
            onTogglePin = {}, onToggleFavourite = {}, onToggleLocked = {}, onOverflowAction = {},
            onBold = {}, onItalic = {}, onHeading = {}, onStrikethrough = {}, onBulletList = {},
            onLink = {}, onImage = {}, onAttachment = {}, onCodeBlock = {}, onTogglePreview = {},
            onTagsClick = {}, onFolderClick = {}, onLinksClick = {}, onAddReminder = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    MDNotesTheme(darkTheme = true) {
        NoteEditorContent(
            state = noteEditorState, onBack = {}, onTitleChange = {}, onContentChange = {},
            onTogglePin = {}, onToggleFavourite = {}, onToggleLocked = {}, onOverflowAction = {},
            onBold = {}, onItalic = {}, onHeading = {}, onStrikethrough = {}, onBulletList = {},
            onLink = {}, onImage = {}, onAttachment = {}, onCodeBlock = {}, onTogglePreview = {},
            onTagsClick = {}, onFolderClick = {}, onLinksClick = {}, onAddReminder = {}
        )
    }
}