package com.example.studymate_androiddevelopment.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "task_table",
    foreignKeys = [
        ForeignKey(
            entity = CourseEntity::class,
            parentColumns = ["id"],
            childColumns = ["courseId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["courseId"])]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val description: String? = null,
    val dueDate: Long? = null,
    val courseId: Long? = null,
    val courseName: String? = null,
    val dueDateEpochDay: Long?,
    val isDone: Boolean = false
)
