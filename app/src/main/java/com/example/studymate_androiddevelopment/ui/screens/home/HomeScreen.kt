package com.example.studymate_androiddevelopment.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGoToCourses: () -> Unit,
    onGoToTasks: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("StudyMate") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Welcome! (Dummy data for now)", style = MaterialTheme.typography.titleMedium)

            Button(onClick = onGoToCourses, modifier = Modifier.fillMaxWidth()) {
                Text("Go to Courses")
            }
            Button(onClick = onGoToTasks, modifier = Modifier.fillMaxWidth()) {
                Text("Go to Tasks")
            }
        }
    }
}