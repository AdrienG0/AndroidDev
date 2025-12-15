package com.example.studymate_androiddevelopment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymate_androiddevelopment.data.local.entity.CourseEntity
import com.example.studymate_androiddevelopment.data.repository.StudyRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(
    private val repository: StudyRepository
) : ViewModel() {

    val courses = repository.getCourses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addCourse(name: String, color: Int?) {
        viewModelScope.launch {
            repository.addCourse(CourseEntity(name = name, color = color))
        }
    }

    fun deleteCourse(courseId: Long) {
        viewModelScope.launch {
            repository.deleteCourse(courseId)
        }
    }
}
