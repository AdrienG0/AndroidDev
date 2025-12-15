package com.example.studymate_androiddevelopment.data.repository

import com.example.studymate_androiddevelopment.data.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InMemoryCoursesRepository : CoursesRepository {

    private val _coursesFlow = MutableStateFlow(
        listOf(
            Course(1, "Android"),
            Course(2, "Network"),
            Course(3, "Linux")
        )
    )
    override val coursesFlow: StateFlow<List<Course>> = _coursesFlow

    private var nextId = 4L

    override fun addCourse(name: String) {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return

        _coursesFlow.value = _coursesFlow.value + Course(nextId++, trimmed)
    }

    override fun deleteCourse(courseId: Long) {
        _coursesFlow.value = _coursesFlow.value.filterNot { it.id == courseId }
    }
}
