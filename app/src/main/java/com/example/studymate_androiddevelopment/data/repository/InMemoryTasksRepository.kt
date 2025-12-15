package com.example.studymate_androiddevelopment.data.repository

import com.example.studymate_androiddevelopment.data.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InMemoryTasksRepository : TasksRepository {

    private val _tasksFlow = MutableStateFlow<List<Task>>(emptyList())
    override val tasksFlow: StateFlow<List<Task>> = _tasksFlow

    private var nextId = 1L

    override fun addTask(title: String, deadline: String, courseId: Long) {
        val t = title.trim()
        val d = deadline.trim()
        if (t.isBlank() || d.isBlank()) return

        val newTask = Task(
            id = nextId++,
            title = t,
            deadline = d,
            courseId = courseId
        )
        _tasksFlow.value = _tasksFlow.value + newTask
    }

    override fun toggleDone(taskId: Long) {
        _tasksFlow.value = _tasksFlow.value.map { task ->
            if (task.id == taskId) task.copy(isDone = !task.isDone) else task
        }
    }

    override fun deleteTask(taskId: Long) {
        _tasksFlow.value = _tasksFlow.value.filterNot { it.id == taskId }
    }
}
