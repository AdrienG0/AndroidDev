package com.example.studymate_androiddevelopment.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studymate_androiddevelopment.domain.RiskLevel

@Composable
fun RiskChip(
    risk: RiskLevel,
    modifier: Modifier = Modifier
) {
    val label = when (risk) {
        RiskLevel.HIGH -> "High"
        RiskLevel.MEDIUM -> "Medium"
        RiskLevel.LOW -> "Low"
    }

    AssistChip(
        onClick = { /* no action needed */ },
        label = { Text(label) },
        modifier = modifier.padding(start = 8.dp),
        colors = AssistChipDefaults.assistChipColors(
            labelColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
