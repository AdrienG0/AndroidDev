package com.example.studymate_androiddevelopment.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
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
    val (label, containerColor, labelColor) = when (risk) {
        RiskLevel.HIGH -> Triple(
            "High",
            Color(0xFFD32F2F), // red
            Color.White
        )

        RiskLevel.MEDIUM -> Triple(
            "Medium",
            Color(0xFFFFA000), // orange
            Color.Black
        )

        RiskLevel.LOW -> Triple(
            "Low",
            Color(0xFF388E3C), // green
            Color.White
        )
    }

    AssistChip(
        onClick = {},
        label = { Text("Risk: $label") },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor,
            labelColor = labelColor
        )
    )
}
