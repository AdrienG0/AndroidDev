package com.example.studymate_androiddevelopment.navigation

sealed class Route(val path: String) {
    data object Home : Route("home")
    data object Courses : Route("courses")
    data object Tasks : Route("tasks")
}