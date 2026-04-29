package com.hieu10.mdnotes.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.components.BottomNavBar
import com.hieu10.mdnotes.ui.components.CreateActionButton
import com.hieu10.mdnotes.ui.navigation.BottomNavTab
import com.hieu10.mdnotes.ui.screens.placeholders.PlaceholderScreen
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(BottomNavTab.HOME) }
    var fabExpanded by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                fabExpanded = fabExpanded,
                onFabClick = { fabExpanded = !fabExpanded }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            // ── Page content ─────────────────
            when (selectedTab) {
                BottomNavTab.HOME -> PlaceholderScreen(title = "Home")
                BottomNavTab.FOLDERS_TAGS -> PlaceholderScreen(title = "Folders & tags")
                BottomNavTab.CALENDAR -> PlaceholderScreen(title = "Calendar")
                BottomNavTab.PROFILE_SETTINGS -> PlaceholderScreen(title = "Profile / Settings")
            }

            // ── Scrim (covers content, disappears on tap) ──
            if (fabExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(alpha = 0.6f))
                        .clickable { fabExpanded = false }
                )
            }

            // ── Create‑action buttons (above bottom bar) ──
            AnimatedVisibility(
                visible = fabExpanded,
                enter = fadeIn() + scaleIn(transformOrigin = TransformOrigin(0.5f, 1f)),
                exit = fadeOut() + scaleOut(transformOrigin = TransformOrigin(0.5f, 1f)),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CreateActionButton(
                        icon = Icons.AutoMirrored.Filled.NoteAdd,
                        label = stringResource(id = R.string.create_action_note),
                        onClick = {
                            /* TODO: start note creation */
                            fabExpanded = false
                        }
                    )
                    CreateActionButton(
                        icon = Icons.Filled.CreateNewFolder,
                        label = stringResource(id = R.string.create_action_folder),
                        onClick = {
                            /* TODO: start folder creation */
                            fabExpanded = false
                        }
                    )
                    CreateActionButton(
                        icon = Icons.AutoMirrored.Filled.Label,
                        label = stringResource(id = R.string.create_action_tag),
                        onClick = {
                            /* TODO: start tag creation */
                            fabExpanded = false
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    MDNotesTheme(darkTheme = false) {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    MDNotesTheme(darkTheme = true) {
        MainScreen()
    }
}