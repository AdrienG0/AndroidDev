package com.example.studymate_androiddevelopment.ui.screens.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

data class UiTask(
    val id: Long,
    val title: String,
    val courseName: String,
    val deadlineText: String,
    val riskLabel: String,
    val isDone: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    taskViewModel: TaskViewModel,
    onAddTask: () -> Unit,
    onOpenCourses: () -> Unit,
    onOpenSettings: () -> Unit,
    onEditTask: (Long) -> Unit
) {
    val taskEntities by taskViewModel.tasks.collectAsState()

    val tasks = remember(taskEntities) {
        taskEntities.map { t ->
            UiTask(
                id = t.id,
                title = t.title,
                courseName = t.courseName ?: "No course",
                deadlineText = t.dueDate?.let { formatMillisToDate(it) } ?: "No deadline",
                riskLabel = "Medium",
                isDone = t.isDone
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StudyMate") },
                actions = {
                    IconButton(onClick = onOpenCourses) {
                        Icon(Icons.Default.List, contentDescription = "Courses")
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
                    TaskCard(
                        task = task,
                        onClick = { onEditTask(task.id) }, // ✅ send id
                        onToggleDone = { newValue ->
                            taskViewModel.toggleDone(task.id, newValue)
                        },
                        onDelete = {
                            taskViewModel.deleteTask(task.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskCard(
    task: UiTask,
    onClick: () -> Unit,
    onToggleDone: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = { onToggleDone(it) }
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text("Course: ${task.courseName}", style = MaterialTheme.typography.bodyMedium)
            Text("Deadline: ${task.deadlineText}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))
            AssistChip(
                onClick = { },
                label = { Text("Risk: ${task.riskLabel}") }
            )

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete")
            }
        }
    }
}
