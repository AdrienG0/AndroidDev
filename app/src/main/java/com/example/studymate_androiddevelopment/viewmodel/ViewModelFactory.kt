package com.example.studymate_androiddevelopment.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studymate_androiddevelopment.data.local.db.DatabaseProvider
import com.example.studymate_androiddevelopment.data.repository.StudyRepository

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val repository: StudyRepository by lazy {
        val db = DatabaseProvider.getDatabase(context)
        StudyRepository(db.courseDao(), db.taskDao())
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TaskViewModel::class.java) ->
                TaskViewModel(repository) as T

            modelClass.isAssignableFrom(CourseViewModel::class.java) ->
                CourseViewModel(repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
