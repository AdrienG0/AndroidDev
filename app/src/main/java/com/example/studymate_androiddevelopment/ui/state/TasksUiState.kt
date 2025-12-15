package com.example.studymate_androiddevelopment.ui.state

import com.example.studymate_androiddevelopment.data.model.Task

data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val filter: TaskFilter = TaskFilter.All,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
