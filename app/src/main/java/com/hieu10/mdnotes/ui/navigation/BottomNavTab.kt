package com.hieu10.mdnotes.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.hieu10.mdnotes.R

enum class BottomNavTab(
    val label: Int,
    val iconOutlined: ImageVector,
    val iconFilled: ImageVector
) {
    HOME(
        label = R.string.nav_home,
        iconOutlined = Icons.Outlined.Home,
        iconFilled = Icons.Filled.Home
    ),
    FOLDERS_TAGS(
        label = R.string.nav_folders,
        iconOutlined = Icons.Outlined.Folder,
        iconFilled = Icons.Filled.Folder
    ),
    CALENDAR(
        label = R.string.nav_calendar,
        iconOutlined = Icons.Outlined.CalendarMonth,
        iconFilled = Icons.Filled.CalendarMonth
    ),
    PROFILE_SETTINGS(
        label = R.string.nav_profile,
        iconOutlined = Icons.Outlined.Person,
        iconFilled = Icons.Filled.Person
    )
}