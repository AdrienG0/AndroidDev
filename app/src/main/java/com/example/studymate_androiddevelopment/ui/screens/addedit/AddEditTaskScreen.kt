package com.example.studymate_androiddevelopment.ui.screens.addedit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studymate_androiddevelopment.viewmodel.CourseViewModel
import com.example.studymate_androiddevelopment.viewmodel.TaskViewModel
import com.example.studymate_androiddevelopment.ui.events.TasksEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskViewModel: TaskViewModel,
    courseViewModel: CourseViewModel,   // ✅ ADD
    onBack: () -> Unit
) {
    val courseEntities by courseViewModel.courses.collectAsState()

    var title by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    var selectedCourse by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    var errorText by remember { mutableStateOf<String?>(null) }

    // Auto-select first course when list is loaded
    LaunchedEffect(courseEntities) {
        if (selectedCourse == null && courseEntities.isNotEmpty()) {
            selectedCourse = courseEntities.first().name
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add / Edit Task") },
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

                    taskViewModel.onEvent(
                        TasksEvent.AddTask(
                            title = cleanTitle,
                            description = null,
                            dueDate = dueDateMillis,
                            courseId = null,
                            courseName = selectedCourse
                        )
                    )

                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }

        }
    }
}
