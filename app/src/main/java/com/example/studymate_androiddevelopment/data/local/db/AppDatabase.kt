package com.example.studymate_androiddevelopment.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.studymate_androiddevelopment.data.local.dao.CourseDao
import com.example.studymate_androiddevelopment.data.local.dao.TaskDao
import com.example.studymate_androiddevelopment.data.local.entity.CourseEntity
import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity

@Database(
    entities = [CourseEntity::class, TaskEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun taskDao(): TaskDao
}
