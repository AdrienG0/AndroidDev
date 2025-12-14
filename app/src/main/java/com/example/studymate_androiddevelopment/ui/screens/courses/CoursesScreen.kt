package com.example.studymate_androiddevelopment.ui.screens.courses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(
    onBack: () -> Unit
) {
    var courses by remember { mutableStateOf(listOf("Android", "Network")) }
    var newCourse by remember { mutableStateOf("") }

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
                onValueChange = { newCourse = it },
                label = { Text("New course name") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (newCourse.isNotBlank()) {
                        courses = courses + newCourse.trim()
                        newCourse = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add course (visual)")
            }

            Divider()

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(courses) { course ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = course,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
