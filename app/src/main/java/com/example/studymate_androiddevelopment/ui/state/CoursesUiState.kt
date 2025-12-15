package com.example.studymate_androiddevelopment.ui.state

import com.example.studymate_androiddevelopment.data.model.Course

data class CoursesUiState(
    val courses: List<Course> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
