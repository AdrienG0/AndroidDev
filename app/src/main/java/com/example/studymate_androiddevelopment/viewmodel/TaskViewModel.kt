package com.example.studymate_androiddevelopment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity
import com.example.studymate_androiddevelopment.data.repository.StudyRepository
import com.example.studymate_androiddevelopment.domain.RiskCalculator
import com.example.studymate_androiddevelopment.domain.RiskLevel
import com.example.studymate_androiddevelopment.ui.events.TasksEvent
import com.example.studymate_androiddevelopment.ui.state.RiskFilter
import com.example.studymate_androiddevelopment.ui.state.SortMode
import com.example.studymate_androiddevelopment.ui.state.TaskFilter
import com.example.studymate_androiddevelopment.ui.state.TasksUiState
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
        }
    }

    private fun setError(message: String) {
        _uiState.update { it.copy(errorMessage = message) }
    }

    private fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
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
            else -> {
                clearError()
            }
        }

        viewModelScope.launch {
            val dueDateEpochDay: Long? = dueDate.let { millis ->
                val tz = TimeZone.getDefault()
                val offset = tz.getOffset(millis)
                val localMillis = millis + offset
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
            } catch (e: Exception) {
                setError("Something went wrong while saving the task.")
            }

        }
    }




    private fun toggleDone(taskId: Long, newValue: Boolean) {
        viewModelScope.launch {
            repository.setTaskDone(taskId, newValue)
        }
    }

    private fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    private fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    private fun applyAll(all: List<TaskEntity>, state: TasksUiState): List<TaskEntity> {
        val afterMainFilter = applyMainFilter(all, state.filter)
        val afterRiskFilter = applyRiskFilter(afterMainFilter, state.riskFilter)
        return applySort(afterRiskFilter, state.sortMode)
    }

    private fun applyMainFilter(all: List<TaskEntity>, filter: TaskFilter): List<TaskEntity> {
        return when (filter) {
            TaskFilter.All -> all
            TaskFilter.Completed -> all.filter { it.isDone }
            TaskFilter.NotCompleted -> all.filter { !it.isDone }
            is TaskFilter.ByCourse -> all.filter { it.courseId == filter.courseId }
        }
    }

    private fun applyRiskFilter(
        all: List<TaskEntity>,
        riskFilter: RiskFilter
    ): List<TaskEntity> {
        if (riskFilter == RiskFilter.All) return all

        return all.filter { task ->
            val risk = RiskCalculator.calculate(task.dueDateEpochDay)
            when (riskFilter) {
                RiskFilter.High -> risk == RiskLevel.HIGH
                RiskFilter.Medium -> risk == RiskLevel.MEDIUM
                RiskFilter.Low -> risk == RiskLevel.LOW
                RiskFilter.All -> true
            }
        }
    }


    private fun applySort(all: List<TaskEntity>, sortMode: SortMode): List<TaskEntity> {
        return when (sortMode) {
            SortMode.DueDateAsc -> {
                all.sortedWith(compareBy<TaskEntity> { it.dueDate == null }.thenBy { it.dueDate ?: Long.MAX_VALUE })
            }

            SortMode.RiskHighFirst -> {
                all.sortedBy { task ->
                    when (RiskCalculator.calculate(task.dueDateEpochDay)) {
                        RiskLevel.HIGH -> 0
                        RiskLevel.MEDIUM -> 1
                        RiskLevel.LOW -> 2
                    }
                }
            }
        }
    }
}
