package com.example.studymate_androiddevelopment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.studymate_androiddevelopment.navigation.AppNavGraph
import com.example.studymate_androiddevelopment.ui.theme.StudyMate_AndroidDevelopmentTheme
import com.example.studymate_androiddevelopment.viewmodel.CourseViewModel
import com.example.studymate_androiddevelopment.viewmodel.TaskViewModel
import com.example.studymate_androiddevelopment.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = ViewModelFactory(applicationContext)

        val taskViewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]
        val courseViewModel = ViewModelProvider(this, factory)[CourseViewModel::class.java]

        setContent {
            StudyMate_AndroidDevelopmentTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    AppNavGraph(
                        navController = navController,
                        taskViewModel = taskViewModel,
                        courseViewModel = courseViewModel
                    )
                }
            }
        }
    }
}
