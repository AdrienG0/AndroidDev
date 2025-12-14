package com.example.studymate_androiddevelopment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studymate_androiddevelopment.ui.home.HomeScreen
import com.example.studymate_androiddevelopment.ui.tasks.TasksScreen



object Routes {
    const val HOME = "home"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home.path
    ) {
        composable(Route.Home.path) {
            HomeScreen(
                onGoToCourses = { navController.navigate(Route.Courses.path) },
                onGoToTasks = { navController.navigate(Route.Tasks.path) }
            )
        }
        composable(Route.Courses.path) {
            CoursesScreen(onBack = { navController.popBackStack() })
        }
        composable(Route.Tasks.path) {
            TasksScreen(onBack = { navController.popBackStack() })
        }
    }
}
