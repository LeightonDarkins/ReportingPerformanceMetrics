data class PerformanceMetricLine(
    val siteName: String,
    val reportName: String,
    val templateName: String,
    val hits: Int,
    val totalTime: Double,
    val averageTime: Double
)