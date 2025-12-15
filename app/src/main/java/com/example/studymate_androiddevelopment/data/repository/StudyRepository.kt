package com.example.studymate_androiddevelopment.data.repository

import com.example.studymate_androiddevelopment.data.local.dao.CourseDao
import com.example.studymate_androiddevelopment.data.local.dao.TaskDao
import com.example.studymate_androiddevelopment.data.local.entity.CourseEntity
import com.example.studymate_androiddevelopment.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

class StudyRepository(
    private val courseDao: CourseDao,
    private val taskDao: TaskDao
) {

    fun getCourses(): Flow<List<CourseEntity>> =
        courseDao.getAllCourses()

    suspend fun addCourse(course: CourseEntity): Long =
        courseDao.insertCourse(course)

    suspend fun deleteCourse(courseId: Long) {
        taskDao.clearCourseForTasks(courseId)

        courseDao.deleteCourse(courseId)
    }

    fun getTasks(): Flow<List<TaskEntity>> =
        taskDao.getAllTasks()

    suspend fun getTaskById(id: Long): TaskEntity? =
        taskDao.getTaskById(id)

    suspend fun addTask(task: TaskEntity): Long =
        taskDao.insertTask(task)

    suspend fun updateTask(task: TaskEntity) =
        taskDao.updateTask(task)

    suspend fun deleteTask(taskId: Long) =
        taskDao.deleteTask(taskId)

    suspend fun setTaskDone(taskId: Long, done: Boolean) =
        taskDao.setDone(taskId, done)
}
