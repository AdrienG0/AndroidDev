package com.example.studymate_androiddevelopment.data.model

data class Task(
    val id: Long,
    val title: String,
    val deadline: String,
    val courseId: Long,
    val isDone: Boolean = false
)