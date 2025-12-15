package com.example.studymate_androiddevelopment.data.repository

import com.example.studymate_androiddevelopment.data.model.Task
import kotlinx.coroutines.flow.StateFlow

interface TasksRepository {
    val tasksFlow: StateFlow<List<Task>>
    fun addTask(title: String, deadline: String, courseId: Long)
    fun toggleDone(taskId: Long)
    fun deleteTask(taskId: Long)
}
