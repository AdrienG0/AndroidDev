package com.example.studymate_androiddevelopment.domain

object RiskCalculator {

    /**
     * Rules:
     * - If dueDate is in the past -> OVERDUE
     * - dueDate is close (0–2 days) -> HIGH
     * - 3–7 days -> MEDIUM
     * - 8+ days -> LOW
     */
    fun calculate(dueDateEpochDay: Long?): RiskLevel {
        if (dueDateEpochDay == null) return RiskLevel.LOW

        val todayEpochDay = System.currentTimeMillis() / 86_400_000L
        val daysLeft = dueDateEpochDay - todayEpochDay

        return when {
            daysLeft < 0 -> RiskLevel.OVERDUE
            daysLeft <= 2 -> RiskLevel.HIGH
            daysLeft >= 3 && daysLeft <= 7 -> RiskLevel.MEDIUM
            else -> RiskLevel.LOW
        }
    }
}
