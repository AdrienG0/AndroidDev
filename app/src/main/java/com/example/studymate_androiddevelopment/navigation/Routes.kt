package com.example.studymate_androiddevelopment.navigation

sealed class Route(val path: String) {
    data object Home : Route("home")
    data object Courses : Route("courses")
    data object Tasks : Route("tasks")
}

object Routes {
    const val TASK_LIST = "tasks"
    const val ADD_EDIT_TASK = "addEditTask"
    const val ADD_EDIT_TASK_WITH_ID = "addEditTask/{taskId}"
    const val COURSES = "courses"
    const val SETTINGS = "settings"

    fun editTask(taskId: Long) = "addEditTask/$taskId"
}
