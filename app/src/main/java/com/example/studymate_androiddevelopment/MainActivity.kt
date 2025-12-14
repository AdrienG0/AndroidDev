package com.example.studymate_androiddevelopment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.studymate_androiddevelopment.navigation.AppNavGraph
import com.example.studymate_androiddevelopment.ui.theme.StudyMate_AndroidDevelopmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyMate_AndroidDevelopmentTheme {
                AppNavGraph()
            }
        }
    }
}