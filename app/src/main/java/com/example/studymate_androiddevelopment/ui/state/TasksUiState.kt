package com.example.studymate_androiddevelopment.ui.state

import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity

data class TasksUiState(
    val tasks: List<TaskEntity> = emptyList(),
    val filter: TaskFilter = TaskFilter.All,
    val riskFilter: RiskFilter = RiskFilter.All,
    val sortMode: SortMode = SortMode.DueDateAsc,

    val errorMessage: String? = null,
    val isFocusModeEnabled: Boolean = false,
    val taskSaved: Boolean = false
)
