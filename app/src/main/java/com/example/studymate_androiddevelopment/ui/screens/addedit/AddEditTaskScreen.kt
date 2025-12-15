package com.example.studymate_androiddevelopment.ui.screens.addedit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studymate_androiddevelopment.ui.events.TasksEvent
import com.example.studymate_androiddevelopment.viewmodel.CourseViewModel
import com.example.studymate_androiddevelopment.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private fun formatMillisToDate(millis: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskId: Long?,
    taskViewModel: TaskViewModel,
    courseViewModel: CourseViewModel,
    onBack: () -> Unit
) {
    val courseEntities by courseViewModel.courses.collectAsState()

    val tasksState by taskViewModel.uiState.collectAsState()
    val taskToEdit = remember(tasksState.tasks, taskId) {
        tasksState.tasks.firstOrNull { it.id == taskId }
    }

    var title by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    var selectedCourse by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    var errorText by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(taskToEdit) {
        if (taskToEdit != null) {
            title = taskToEdit.title
            deadline = taskToEdit.dueDate?.let { formatMillisToDate(it) } ?: ""
            selectedCourse = taskToEdit.courseName
        }
    }

    LaunchedEffect(courseEntities, taskToEdit) {
        if (taskToEdit == null) {
            if (selectedCourse == null && courseEntities.isNotEmpty()) {
                selectedCourse = courseEntities.first().name
            }
        } else {
            if (selectedCourse == null && courseEntities.isNotEmpty()) {
                selectedCourse = courseEntities.first().name
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskToEdit == null) "Add Task" else "Edit Task") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    errorText = null
                },
                label = { Text("Task title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = deadline,
                onValueChange = { deadline = it },
                label = { Text("Deadline (example: 2025-12-20)") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCourse ?: "No courses yet",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Course") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    courseEntities.forEach { course ->
                        DropdownMenuItem(
                            text = { Text(course.name) },
                            onClick = {
                                selectedCourse = course.name
                                expanded = false
                            }
                        )
                    }
                }
            }

            if (errorText != null) {
                Text(
                    text = errorText!!,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    val cleanTitle = title.trim()
                    val dueDateMillis = parseDateToMillis(deadline)

                    if (cleanTitle.isEmpty()) {
                        errorText = "Title is required."
                        return@Button
                    }

                    if (taskToEdit == null) {
                        // ✅ ADD MODE
                        taskViewModel.onEvent(
                            TasksEvent.AddTask(
                                title = cleanTitle,
                                description = null,
                                dueDate = dueDateMillis,
                                courseId = null,
                                courseName = selectedCourse
                            )
                        )
                    } else {
                        val updatedTask = taskToEdit.copy(
                            title = cleanTitle,
                            description = taskToEdit.description, // keep existing
                            dueDate = dueDateMillis,
                            courseId = null,
                            courseName = selectedCourse
                        )

                        taskViewModel.onEvent(
                            TasksEvent.UpdateTask(updatedTask)
                        )
                    }

                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (taskToEdit == null) "Save" else "Update")
            }
        }
    }
}
