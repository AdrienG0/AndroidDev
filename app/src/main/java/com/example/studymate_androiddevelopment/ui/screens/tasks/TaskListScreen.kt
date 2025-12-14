package com.example.studymate_androiddevelopment.ui.screens.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class UiTask(
    val title: String,
    val courseName: String,
    val deadlineText: String,
    val riskLabel: String // later: Low/Medium/High
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onAddTask: () -> Unit,
    onOpenCourses: () -> Unit,
    onOpenSettings: () -> Unit,
    onEditTask: () -> Unit
) {
    // Dummy tasks for Phase 4
    var tasks by remember {
        mutableStateOf(
            listOf(
                UiTask("Finish Android UI", "Android", "Tomorrow", "High"),
                UiTask("Read networking notes", "Network", "Next week", "Low")
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StudyMate") },
                actions = {
                    IconButton(onClick = onOpenCourses) {
                        Icon(Icons.Default.MenuBook, contentDescription = "Courses")
                    }
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Default.Add, contentDescription = "Add task")
            }
        }
    ) { padding ->
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No tasks yet. Tap + to add one.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tasks) { task ->
                    TaskCard(task = task, onClick = onEditTask)
                }
            }
        }
    }
}

@Composable
private fun TaskCard(
    task: UiTask,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(task.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Course: ${task.courseName}", style = MaterialTheme.typography.bodyMedium)
            Text("Deadline: ${task.deadlineText}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            AssistChip(
                onClick = { },
                label = { Text("Risk: ${task.riskLabel}") }
            )
        }
    }
}
