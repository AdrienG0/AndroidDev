package com.example.studymate_androiddevelopment.data.repository

import com.example.studymate_androiddevelopment.data.model.Course
import kotlinx.coroutines.flow.StateFlow

interface CoursesRepository {
    val coursesFlow: StateFlow<List<Course>>
    fun addCourse(name: String)
    fun deleteCourse(courseId: Long)
}
