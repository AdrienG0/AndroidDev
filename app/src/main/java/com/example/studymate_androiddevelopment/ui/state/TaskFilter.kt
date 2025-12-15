package com.example.studymate_androiddevelopment.ui.state

sealed class TaskFilter {
    data object All : TaskFilter()
    data object Today : TaskFilter()
    data object Completed : TaskFilter()
    data class ByCourse(val courseId: Long) : TaskFilter()
}
