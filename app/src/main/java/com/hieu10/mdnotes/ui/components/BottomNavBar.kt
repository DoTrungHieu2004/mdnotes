package com.hieu10.mdnotes.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.mdnotes.R
import com.hieu10.mdnotes.ui.navigation.BottomNavTab
import com.hieu10.mdnotes.ui.theme.MDNotesTheme

enum class CreateAction { NOTE, FOLDER, TAG }

@Composable
fun BottomNavBar(
    selectedTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit,
    fabExpanded: Boolean,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (fabExpanded) 45f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    Box(modifier = modifier.fillMaxWidth()) {
        Box(modifier = modifier.fillMaxWidth()) {
            NavigationBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                BottomNavTab.entries.forEach { tab ->
                    val selected = tab == selectedTab
                    NavigationBarItem(
                        selected = selected,
                        onClick = { onTabSelected(tab) },
                        icon = {
                            Icon(
                                imageVector = if (selected) tab.iconFilled else tab.iconOutlined,
                                contentDescription = stringResource(id = tab.label)
                            )
                        },
                        label = { Text(text = stringResource(id = tab.label)) }
                    )
                }
            }

            // Centred FAB lifted above the bar
            FloatingActionButton(
                onClick = onFabClick,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-28).dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.cd_create),
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomBarLight() {
    MDNotesTheme(darkTheme = false) {
        BottomNavBar(
            selectedTab = BottomNavTab.HOME,
            onTabSelected = {},
            fabExpanded = false,
            onFabClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomBarDark() {
    MDNotesTheme(darkTheme = true) {
        BottomNavBar(
            selectedTab = BottomNavTab.HOME,
            onTabSelected = {},
            fabExpanded = false,
            onFabClick = {}
        )
    }
}