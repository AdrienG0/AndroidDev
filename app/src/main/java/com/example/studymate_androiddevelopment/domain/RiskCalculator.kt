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
    fun calculate(dueDate: LocalDate, today: LocalDate = LocalDate.now()): RiskLevel {
        val daysLeft = ChronoUnit.DAYS.between(today, dueDate)

        return when {
            daysLeft <= 2 -> RiskLevel.HIGH
            daysLeft in 3..7 -> RiskLevel.MEDIUM
            else -> RiskLevel.LOW
        }
    }
}
