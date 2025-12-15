package com.example.studymate_androiddevelopment.ui.events

sealed class CoursesEvent {
    data class AddCourse(val name: String) : CoursesEvent()
    data class DeleteCourse(val courseId: Long) : CoursesEvent()
}
