package com.hieu10.mdnotes.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun formatDate(epoch: Long): String {
    val sdf = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
    return sdf.format(Date(epoch))
}

fun formatLocalDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    return date.format(formatter)
}