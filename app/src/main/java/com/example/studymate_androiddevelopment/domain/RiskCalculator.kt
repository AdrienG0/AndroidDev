package com.example.studymate_androiddevelopment.domain

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object RiskCalculator {

    /**
     * Rules:
     * - dueDate is close (0–2 days) -> HIGH
     * - 3–7 days -> MEDIUM
     * - 8+ days -> LOW
     *
     * If dueDate is in the past -> HIGH (overdue)
     */
    fun calculate(dueDateEpochDay: Long?): RiskLevel {
        if (dueDateEpochDay == null) return RiskLevel.LOW

        val todayEpochDay = System.currentTimeMillis() / 86_400_000L
        val daysLeft = dueDateEpochDay - todayEpochDay

        return when {
            daysLeft <= 2 -> RiskLevel.HIGH
            daysLeft >= 3 && daysLeft <= 7 -> RiskLevel.MEDIUM
            else -> RiskLevel.LOW
        }
    }
}
