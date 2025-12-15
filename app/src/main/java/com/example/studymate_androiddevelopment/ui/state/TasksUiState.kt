package com.example.studymate_androiddevelopment.ui.state

import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity

data class TasksUiState(
    val tasks: List<TaskEntity> = emptyList(),
    val filter: TaskFilter = TaskFilter.All,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
