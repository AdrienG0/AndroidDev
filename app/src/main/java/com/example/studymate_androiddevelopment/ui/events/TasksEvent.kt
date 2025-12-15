package com.example.studymate_androiddevelopment.ui.events

import com.example.studymate_androiddevelopment.ui.state.TaskFilter

sealed class TasksEvent {
    data class AddTask(val title: String, val deadline: String, val courseId: Long) : TasksEvent()
    data class ToggleDone(val taskId: Long) : TasksEvent()
    data class DeleteTask(val taskId: Long) : TasksEvent()
    data class ChangeFilter(val filter: TaskFilter) : TasksEvent()
}
