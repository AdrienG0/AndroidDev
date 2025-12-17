package com.example.studymate_androiddevelopment.ui.screens.addedit

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun parseDateToMillis(dateString: String): Long? {
    val input = dateString.trim()
    if (input.isBlank()) return null

    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        sdf.isLenient = false
        sdf.parse(input)?.time
    } catch (e: Exception) {
        null
    }
}
