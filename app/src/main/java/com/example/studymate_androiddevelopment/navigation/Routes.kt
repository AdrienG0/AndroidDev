package com.example.studymate_androiddevelopment.navigation

sealed class Route(val path: String) {
    data object Home : Route("home")
    data object Courses : Route("courses")
    data object Tasks : Route("tasks")
}

object Routes {
    const val TASK_LIST = "task_list"
    const val ADD_EDIT_TASK = "add_edit_task"
    const val COURSES = "courses"
    const val SETTINGS = "settings"
}