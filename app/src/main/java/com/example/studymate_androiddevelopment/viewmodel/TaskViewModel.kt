package com.example.studymate_androiddevelopment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity
import com.example.studymate_androiddevelopment.data.repository.StudyRepository
import com.example.studymate_androiddevelopment.domain.RiskCalculator
import com.example.studymate_androiddevelopment.domain.RiskLevel
import com.example.studymate_androiddevelopment.ui.events.TasksEvent
import com.example.studymate_androiddevelopment.ui.state.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.TimeZone

class TaskViewModel(
    private val repository: StudyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.getTasks().collect { allTasks ->
                val finalList = applyAll(allTasks, _uiState.value)
                _uiState.update { it.copy(tasks = finalList) }
            }
        }
    }

    fun onEvent(event: TasksEvent) {
        when (event) {

            is TasksEvent.AddTask -> addTask(
                event.title,
                event.description,
                event.dueDate,
                event.courseId,
                event.courseName
            )

            is TasksEvent.ToggleDone -> toggleDone(event.taskId, event.newValue)

            is TasksEvent.DeleteTask -> deleteTask(event.taskId)

            is TasksEvent.UpdateTask -> updateTask(event.task)

            is TasksEvent.ChangeFilter -> {
                _uiState.update { it.copy(filter = event.filter) }
                refreshOnce()
            }

            is TasksEvent.ChangeRiskFilter -> {
                _uiState.update { it.copy(riskFilter = event.riskFilter) }
                refreshOnce()
            }

            is TasksEvent.ChangeSortMode -> {
                _uiState.update { it.copy(sortMode = event.sortMode) }
                refreshOnce()
            }
            is TasksEvent.ToggleFocusMode -> {
                _uiState.update { it.copy(isFocusModeEnabled = event.enabled) }
                refreshOnce()
            }
        }
    }

    private fun setError(message: String) {
        _uiState.update { it.copy(errorMessage = message, taskSaved = false) }
    }

    private fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun consumeTaskSaved() {
        _uiState.update { it.copy(taskSaved = false) }
    }

    private fun refreshOnce() {
        viewModelScope.launch {
            repository.getTasks().collect { tasks ->
                val finalList = applyAll(tasks, _uiState.value)
                _uiState.update { it.copy(tasks = finalList) }
                return@collect
            }
        }
    }

    private fun addTask(
        title: String,
        description: String?,
        dueDate: Long?,
        courseId: Long?,
        courseName: String?
    ) {
        _uiState.update { it.copy(taskSaved = false) }

        val cleanTitle = title.trim()
        val cleanCourseName = courseName?.trim()

        when {
            cleanTitle.isBlank() -> {
                setError("Title is required.")
                return
            }
            dueDate == null -> {
                setError("Deadline is required.")
                return
            }
            courseId == null || cleanCourseName.isNullOrBlank() -> {
                setError("Course is required.")
                return
            }
            else -> clearError()
        }

        viewModelScope.launch {
            val dueDateEpochDay = run {
                val tz = TimeZone.getDefault()
                val offset = tz.getOffset(dueDate)
                val localMillis = dueDate + offset
                Math.floorDiv(localMillis, 86_400_000L)
            }

            val task = TaskEntity(
                title = cleanTitle,
                description = description,
                dueDateEpochDay = dueDateEpochDay,
                courseId = courseId,
                courseName = cleanCourseName
            )

            try {
                repository.addTask(task)
                _uiState.update { it.copy(taskSaved = true) }
            } catch (e: Exception) {
                setError("Something went wrong while saving the task.")
            }
        }
    }

    private fun toggleDone(taskId: Long, newValue: Boolean) {
        viewModelScope.launch {
            try {
                repository.setTaskDone(taskId, newValue)
            } catch (e: Exception) {
                setError("Could not update the task status.")
            }
        }
    }

    private fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            try {
                repository.deleteTask(taskId)
            } catch (e: Exception) {
                setError("Could not delete the task.")
            }
        }
    }

    private fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            try {
                repository.updateTask(task)
                _uiState.update { it.copy(taskSaved = true) }
            } catch (e: Exception) {
                setError("Could not update the task.")
            }
        }
    }

    private fun applyAll(all: List<TaskEntity>, state: TasksUiState): List<TaskEntity> {
        val afterMainFilter = applyMainFilter(all, state.filter)
        val afterFocusMode = applyFocusMode(afterMainFilter, state.isFocusModeEnabled)
        val afterRiskFilter = applyRiskFilter(afterFocusMode, state.riskFilter)
        return applySort(afterRiskFilter, state.sortMode)
    }

    private fun applyMainFilter(all: List<TaskEntity>, filter: TaskFilter): List<TaskEntity> =
        when (filter) {
            TaskFilter.All -> all
            TaskFilter.Completed -> all.filter { it.isDone }
            TaskFilter.NotCompleted -> all.filter { !it.isDone }
            is TaskFilter.ByCourse -> all.filter { it.courseId == filter.courseId }
        }

    private fun applyRiskFilter(
        all: List<TaskEntity>,
        riskFilter: RiskFilter
    ): List<TaskEntity> {
        if (riskFilter == RiskFilter.All) return all

        return all.filter { task ->
            when (riskFilter) {
                RiskFilter.Overdue -> RiskCalculator.calculate(task.dueDateEpochDay) == RiskLevel.OVERDUE
                RiskFilter.High -> RiskCalculator.calculate(task.dueDateEpochDay) == RiskLevel.HIGH
                RiskFilter.Medium -> RiskCalculator.calculate(task.dueDateEpochDay) == RiskLevel.MEDIUM
                RiskFilter.Low -> RiskCalculator.calculate(task.dueDateEpochDay) == RiskLevel.LOW
                RiskFilter.All -> true
            }
        }
    }

    private fun applyFocusMode(all: List<TaskEntity>, enabled: Boolean): List<TaskEntity> {
        if (!enabled) return all

        return all.filter { task ->
            when (RiskCalculator.calculate(task.dueDateEpochDay)) {
                RiskLevel.OVERDUE, RiskLevel.HIGH, RiskLevel.MEDIUM -> true
                RiskLevel.LOW -> false
            }
        }
    }

    private fun applySort(all: List<TaskEntity>, sortMode: SortMode): List<TaskEntity> =
        when (sortMode) {
            SortMode.DueDateAsc ->
                all.sortedWith(
                    compareBy<TaskEntity> { it.dueDateEpochDay == null }
                        .thenBy { it.dueDateEpochDay ?: Long.MAX_VALUE }
                )

            SortMode.RiskHighFirst ->
                all.sortedBy {
                    when (RiskCalculator.calculate(it.dueDateEpochDay)) {
                        RiskLevel.OVERDUE -> 0
                        RiskLevel.HIGH -> 1
                        RiskLevel.MEDIUM -> 2
                        RiskLevel.LOW -> 3
                    }
                }
        }
}