package com.example.studymate_androiddevelopment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY isDone ASC, dueDate ASC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task_table WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: Long): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM task_table WHERE id = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Query("UPDATE task_table SET isDone = :done WHERE id = :taskId")
    suspend fun setDone(taskId: Long, done: Boolean)

    @Query("UPDATE task_table SET courseId = NULL, courseName = NULL WHERE courseId = :courseId")
    suspend fun clearCourseForTasks(courseId: Long)

}
