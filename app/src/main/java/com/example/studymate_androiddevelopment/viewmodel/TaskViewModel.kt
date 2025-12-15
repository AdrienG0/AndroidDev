package com.example.studymate_androiddevelopment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity
import com.example.studymate_androiddevelopment.data.repository.StudyRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: StudyRepository
) : ViewModel() {

    val tasks = repository.getTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTask(title: String, description: String?, dueDate: Long?, courseId: Long?) {
        viewModelScope.launch {
            val task = TaskEntity(
                title = title,
                description = description,
                dueDate = dueDate,
                courseId = courseId
            )
            repository.addTask(task)
        }
    }

    fun toggleDone(taskId: Long, newValue: Boolean) {
        viewModelScope.launch {
            repository.setTaskDone(taskId, newValue)
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }
}
