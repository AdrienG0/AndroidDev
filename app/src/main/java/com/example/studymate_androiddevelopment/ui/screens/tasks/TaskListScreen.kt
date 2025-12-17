package com.example.studymate_androiddevelopment.ui.screens.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studymate_androiddevelopment.domain.RiskCalculator
import com.example.studymate_androiddevelopment.ui.components.RiskChip
import com.example.studymate_androiddevelopment.ui.events.TasksEvent
import com.example.studymate_androiddevelopment.ui.state.RiskFilter
import com.example.studymate_androiddevelopment.ui.state.SortMode
import com.example.studymate_androiddevelopment.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

private fun formatEpochDay(epochDay: Long): String {
    val millis = epochDay * 86_400_000L
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    taskViewModel: TaskViewModel,
    onAddTask: () -> Unit,
    onOpenCourses: () -> Unit,
    onOpenSettings: () -> Unit,
    onEditTask: (Long) -> Unit
) {
    val uiState by taskViewModel.uiState.collectAsState()
    var menuExpanded by remember { mutableStateOf(false) }

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

                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        Text(
                            "Filter by risk",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.labelMedium
                        )

                        DropdownMenuItem(
                            text = { Text("All") },
                            onClick = {
                                taskViewModel.onEvent(
                                    TasksEvent.ChangeRiskFilter(RiskFilter.All)
                                )
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("High") },
                            onClick = {
                                taskViewModel.onEvent(
                                    TasksEvent.ChangeRiskFilter(RiskFilter.High)
                                )
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Medium") },
                            onClick = {
                                taskViewModel.onEvent(
                                    TasksEvent.ChangeRiskFilter(RiskFilter.Medium)
                                )
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Low") },
                            onClick = {
                                taskViewModel.onEvent(
                                    TasksEvent.ChangeRiskFilter(RiskFilter.Low)
                                )
                                menuExpanded = false
                            }
                        )

                        HorizontalDivider()

                        Text(
                            "Sort",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.labelMedium
                        )

                        DropdownMenuItem(
                            text = { Text("By due date") },
                            onClick = {
                                taskViewModel.onEvent(
                                    TasksEvent.ChangeSortMode(SortMode.DueDateAsc)
                                )
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Risk (High first)") },
                            onClick = {
                                taskViewModel.onEvent(
                                    TasksEvent.ChangeSortMode(SortMode.RiskHighFirst)
                                )
                                menuExpanded = false
                            }
                        )
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

        if (uiState.tasks.isEmpty()) {
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
                items(uiState.tasks) { task ->

                    val deadlineText = if (task.dueDateEpochDay == null) {
                        "No deadline"
                    } else {
                        formatEpochDay(task.dueDateEpochDay)
                    }

                    val risk = RiskCalculator.calculate(task.dueDateEpochDay)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onEditTask(task.id) }
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
                                    onCheckedChange = {
                                        taskViewModel.onEvent(
                                            TasksEvent.ToggleDone(task.id, it)
                                        )
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Course: ${task.courseName ?: "No course"}")
                            Text("Deadline: $deadlineText")

                            Spacer(modifier = Modifier.height(8.dp))
                            RiskChip(risk = risk)

                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    taskViewModel.onEvent(
                                        TasksEvent.DeleteTask(task.id)
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
