package com.example.studymate_androiddevelopment.ui.screens.courses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studymate_androiddevelopment.viewmodel.CourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(
    courseViewModel: CourseViewModel,
    onBack: () -> Unit
) {
    val courses by courseViewModel.courses.collectAsState()

    var newCourse by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Courses") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = newCourse,
                onValueChange = {
                    newCourse = it
                    errorText = null
                },
                label = { Text("New course name") },
                modifier = Modifier.fillMaxWidth()
            )

            if (errorText != null) {
                Text(errorText!!, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    val name = newCourse.trim()
                    if (name.isEmpty()) {
                        errorText = "Course name is required."
                        return@Button
                    }

                    courseViewModel.addCourse(name = name, color = null)
                    newCourse = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add course")
            }

            Divider()

            if (courses.isEmpty()) {
                Text("No courses yet.")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(courses) { course ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = course.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.weight(1f)
                                )

                                IconButton(
                                    onClick = { courseViewModel.deleteCourse(course.id) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete course"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
