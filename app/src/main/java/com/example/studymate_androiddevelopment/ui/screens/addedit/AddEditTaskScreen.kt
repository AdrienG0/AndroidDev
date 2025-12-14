package com.example.studymate_androiddevelopment.ui.screens.addedit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    onBack: () -> Unit
) {
    // Dummy courses list for dropdown
    val courses = listOf("Android", "Network", "Linux")

    var title by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    var selectedCourse by remember { mutableStateOf(courses.first()) }
    var expanded by remember { mutableStateOf(false) }

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
                onValueChange = { title = it },
                label = { Text("Task title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = deadline,
                onValueChange = { deadline = it },
                label = { Text("Deadline (example: 2025-12-20)") },
                modifier = Modifier.fillMaxWidth()
            )

            // DropdownMenu (Material 3 style)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCourse,
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
                    courses.forEach { course ->
                        DropdownMenuItem(
                            text = { Text(course) },
                            onClick = {
                                selectedCourse = course
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    // Phase 4: only visual / no DB yet
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save (visual)")
            }
        }
    }
}
