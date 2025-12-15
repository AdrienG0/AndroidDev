package com.example.studymate_androiddevelopment.ui.events

import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity
import com.example.studymate_androiddevelopment.ui.state.TaskFilter

sealed class TasksEvent {
    data class AddTask(
        val title: String,
        val description: String?,
        val dueDate: Long?,
        val courseId: Long?,
        val courseName: String?
    ) : TasksEvent()

    data class ToggleDone(val taskId: Long, val newValue: Boolean) : TasksEvent()
    data class DeleteTask(val taskId: Long) : TasksEvent()
    data class UpdateTask(val task: TaskEntity) : TasksEvent()
    data class ChangeFilter(val filter: TaskFilter) : TasksEvent()
}
