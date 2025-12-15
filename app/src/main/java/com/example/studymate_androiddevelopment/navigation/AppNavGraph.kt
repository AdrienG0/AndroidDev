package com.example.studymate_androiddevelopment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studymate_androiddevelopment.ui.screens.addedit.AddEditTaskScreen
import com.example.studymate_androiddevelopment.ui.screens.courses.CoursesScreen
import com.example.studymate_androiddevelopment.ui.screens.settings.SettingsScreen
import com.example.studymate_androiddevelopment.ui.screens.tasks.TaskListScreen
import com.example.studymate_androiddevelopment.viewmodel.CourseViewModel
import com.example.studymate_androiddevelopment.viewmodel.TaskViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    courseViewModel: CourseViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.TASK_LIST
    ) {
        composable(Routes.TASK_LIST) {
            TaskListScreen(
                taskViewModel = taskViewModel,
                onAddTask = { navController.navigate(Routes.ADD_EDIT_TASK) },
                onOpenCourses = { navController.navigate(Routes.COURSES) },
                onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                onEditTask = { taskId ->
                    navController.navigate(Routes.ADD_EDIT_TASK)
                }
            )
        }

        composable(Routes.ADD_EDIT_TASK) {
            AddEditTaskScreen(
                taskViewModel = taskViewModel,
                onBack = { navController.popBackStack() }
            )
        }


        composable(Routes.COURSES) {
            CoursesScreen(
                courseViewModel = courseViewModel,
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
