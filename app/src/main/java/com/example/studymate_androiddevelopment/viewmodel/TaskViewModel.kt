package com.example.studymate_androiddevelopment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity
import com.example.studymate_androiddevelopment.data.repository.StudyRepository
import com.example.studymate_androiddevelopment.ui.events.TasksEvent
import com.example.studymate_androiddevelopment.ui.state.TaskFilter
import com.example.studymate_androiddevelopment.ui.state.TasksUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: StudyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.getTasks().collect { allTasks ->
                val filtered = applyFilter(allTasks, _uiState.value.filter)
                _uiState.update { it.copy(tasks = filtered) }
            }
        }
    }

    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.AddTask -> addTask(event.title, event.description, event.dueDate, event.courseId, event.courseName)
            is TasksEvent.ToggleDone -> toggleDone(event.taskId, event.newValue)
            is TasksEvent.DeleteTask -> deleteTask(event.taskId)
            is TasksEvent.UpdateTask -> updateTask(event.task)
            is TasksEvent.ChangeFilter -> {
                _uiState.update { it.copy(filter = event.filter) }
                // re-filter using current displayed list? better: pull from repo again
                viewModelScope.launch {
                    val all = repository.getTasks() // Flow
                    all.collect { tasks ->
                        val filtered = applyFilter(tasks, event.filter)
                        _uiState.update { it.copy(tasks = filtered) }
                        return@collect // stop after 1 emission
                    }
                }
            }
        }
    }

    private fun addTask(title: String, description: String?, dueDate: Long?, courseId: Long?, courseName: String?) {
        viewModelScope.launch {
            val task = TaskEntity(
                title = title,
                description = description,
                dueDate = dueDate,
                courseId = courseId,
                courseName = courseName
            )
            repository.addTask(task)
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

    private fun applyFilter(all: List<TaskEntity>, filter: TaskFilter): List<TaskEntity> {
        return when (filter) {
            TaskFilter.All -> all
            TaskFilter.Completed -> all.filter { it.isDone }
            TaskFilter.NotCompleted -> all.filter { !it.isDone }
            is TaskFilter.ByCourse -> all.filter { it.courseId == filter.courseId }
        }
    }
}
