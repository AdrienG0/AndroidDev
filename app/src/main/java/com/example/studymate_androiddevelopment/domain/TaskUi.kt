package com.example.studymate_androiddevelopment.domain

import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity

data class TaskUi(
    val id: Long,
    val title: String,
    val courseName: String?,
    val dueDateEpochDay: Long?,
    val isDone: Boolean,
    val risk: RiskLevel
)

fun TaskEntity.toUi(): TaskUi {
    return TaskUi(
        id = id,
        title = title,
        courseName = courseName,
        dueDateEpochDay = dueDateEpochDay,
        isDone = isDone,
        risk = RiskCalculator.calculate(dueDateEpochDay)
    )
}
