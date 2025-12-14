package com.example.studymate_androiddevelopment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studymate_androiddevelopment.ui.screens.addedit.AddEditTaskScreen
import com.example.studymate_androiddevelopment.ui.home.HomeScreen
import com.example.studymate_androiddevelopment.ui.tasks.TasksScreen
import com.example.studymate_androiddevelopment.ui.courses.CoursesScreen



object Routes {
    const val HOME = "home"
}

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.TASK_LIST
    ) {
        composable(Routes.TASK_LIST) {
            TaskListScreen(
                onAddTask = { navController.navigate(Routes.ADD_EDIT_TASK) },
                onOpenCourses = { navController.navigate(Routes.COURSES) },
                onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                onEditTask = { navController.navigate(Routes.ADD_EDIT_TASK) } // later: pass id
            )
        }

        composable(Routes.ADD_EDIT_TASK) {
            AddEditTaskScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.COURSES) {
            CoursesScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
