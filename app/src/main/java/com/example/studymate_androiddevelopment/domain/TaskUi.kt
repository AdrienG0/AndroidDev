package com.example.studymate_androiddevelopment.domain

import com.example.studymate_androiddevelopment.data.local.entities.TaskEntity
import java.time.LocalDate

data class TaskUi(
    val id: Int,
    val title: String,
    val courseName: String,
    val dueDate: LocalDate,
    val isDone: Boolean,
    val risk: RiskLevel
)

fun TaskEntity.toUi(): TaskUi {
    val date = LocalDate.ofEpochDay(dueDateEpochDay)
    return TaskUi(
        id = id,
        title = title,
        courseName = courseName,
        dueDate = date,
        isDone = isDone,
        risk = RiskCalculator.calculate(date)
    )
}
