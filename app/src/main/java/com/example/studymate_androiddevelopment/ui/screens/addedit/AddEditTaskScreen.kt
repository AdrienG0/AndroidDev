package com.example.studymate_androiddevelopment.ui.screens.addedit

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

private fun formatEpochDayToInput(epochDay: Long): String {
    val millis = epochDay * 86_400_000L
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

    val selectedCourseEntity = courseEntities.firstOrNull { it.name == selectedCourse }
    val selectedCourseId = selectedCourseEntity?.id

    LaunchedEffect(tasksState.taskSaved) {
        if (tasksState.taskSaved) {
            taskViewModel.consumeTaskSaved()
            onBack()
        }
    }

    LaunchedEffect(taskToEdit) {
        if (taskToEdit != null) {
            title = taskToEdit.title
            deadline = taskToEdit.dueDateEpochDay?.let { formatEpochDayToInput(it) } ?: ""
            selectedCourse = taskToEdit.courseName
        }
    }

    LaunchedEffect(courseEntities, taskToEdit) {
        if (taskToEdit == null && selectedCourse == null && courseEntities.isNotEmpty()) {
            selectedCourse = courseEntities.first().name
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
                onValueChange = { title = it },
                label = { Text("Task title") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
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

            tasksState.errorMessage?.let { msg ->
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    val cleanTitle = title.trim()
                    val dueDateMillis = parseDateToMillis(deadline)

                    if (taskToEdit == null) {
                        taskViewModel.onEvent(
                            TasksEvent.AddTask(
                                title = cleanTitle,
                                description = null,
                                dueDate = dueDateMillis,
                                courseId = selectedCourseId,
                                courseName = selectedCourseEntity?.name
                            )
                        )
                    } else {
                        val updatedTask = taskToEdit.copy(
                            title = cleanTitle,
                            description = taskToEdit.description,
                            dueDateEpochDay = dueDateMillis?.let { millis ->
                                val tz = TimeZone.getDefault()
                                val offset = tz.getOffset(millis)
                                val localMillis = millis + offset
                                Math.floorDiv(localMillis, 86_400_000L)
                            },
                            courseId = selectedCourseId,
                            courseName = selectedCourseEntity?.name
                        )

                        taskViewModel.onEvent(
                            TasksEvent.UpdateTask(updatedTask)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = courseEntities.isNotEmpty()
            ) {
                Text(if (taskToEdit == null) "Save" else "Update")
            }

            if (courseEntities.isEmpty()) {
                Text(
                    text = "Create a course first.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
