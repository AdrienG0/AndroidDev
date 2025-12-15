package com.example.studymate_androiddevelopment.ui.state

sealed class TaskFilter {
    data object All : TaskFilter()
    data object Completed : TaskFilter()
    data object NotCompleted : TaskFilter()
    data class ByCourse(val courseId: Long) : TaskFilter()
}
