package com.example.studymate_androiddevelopment.ui.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.studymate_androiddevelopment.domain.RiskLevel

@Composable
fun RiskChip(
    risk: RiskLevel,
    modifier: Modifier = Modifier
) {
    val (label, containerColor, labelColor) = when (risk) {

        RiskLevel.OVERDUE -> Triple(
            "Overdue",
            Color(0xFF6D0000),
            Color.White
        )

        RiskLevel.HIGH -> Triple(
            "High",
            Color(0xFFD32F2F),
            Color.White
        )

        RiskLevel.MEDIUM -> Triple(
            "Medium",
            Color(0xFFFFA000),
            Color.Black
        )

        RiskLevel.LOW -> Triple(
            "Low",
            Color(0xFF388E3C),
            Color.White
        )
    }

    val chipText = if (risk == RiskLevel.OVERDUE) {
        label
    } else {
        "Risk: $label"
    }

    AssistChip(
        onClick = {},
        label = { Text(chipText) },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor,
            labelColor = labelColor
        )
    )
}
