package com.example.studymate_androiddevelopment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                onAddTask = {
                    navController.navigate(Routes.ADD_EDIT_TASK)
                },
                onOpenCourses = {
                    navController.navigate(Routes.COURSES)
                },
                onOpenSettings = {
                    navController.navigate(Routes.SETTINGS)
                },
                onEditTask = { taskId ->
                    navController.navigate(Routes.editTask(taskId))
                }
            )
        }

        composable(Routes.ADD_EDIT_TASK) {
            AddEditTaskScreen(
                taskId = null,
                taskViewModel = taskViewModel,
                courseViewModel = courseViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.ADD_EDIT_TASK_WITH_ID,
            arguments = listOf(
                navArgument("taskId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId")

            AddEditTaskScreen(
                taskId = taskId,
                taskViewModel = taskViewModel,
                courseViewModel = courseViewModel,
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
