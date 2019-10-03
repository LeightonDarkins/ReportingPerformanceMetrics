import java.math.BigDecimal
import java.math.RoundingMode

val AVERAGE_MONTH_LENGTH = BigDecimal(30)
val AVERAGE_DAY_LENGTH = BigDecimal(10)
val NUMBER_OF_CLINICS = BigDecimal(3000)
val SIXTY = BigDecimal(60)

fun main() {
    val inputHandler = InputHandler()
    val performanceMetricLines = inputHandler.read()

    val grouped = performanceMetricLines.groupBy { it.siteName }

    val aggregateMetricLines = grouped.map { (key, value) ->
        val hits = value.sumBy { line -> line.hits }
        val time = value.sumByDouble { line -> line.averageTime }

        AggregateMetricLine(key, hits, time)
    }

    println("--- Hits ---")

    val averageHitsPerMonth = aggregateMetricLines.map { it.hits }.average().toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)

    println("$averageHitsPerMonth- Report Hits Per Month, Per Site")

    val averageHitsPerDayPerClinic = averageHitsPerMonth / AVERAGE_MONTH_LENGTH
    val averageHitsPerDay = averageHitsPerDayPerClinic * NUMBER_OF_CLINICS
    val averageHitsPerHour = averageHitsPerDay / AVERAGE_DAY_LENGTH

    println("$averageHitsPerDayPerClinic Average Hits Per Month, Per Clinic")
    println("$averageHitsPerDay - Average Hits Per Per Month, Overall")
    println("$averageHitsPerHour - Average Hits Per Hour, Per Work Day, Overall")

    println("--- Runtime ---")

    val averageTime = aggregateMetricLines.map { it.averageTime }.average().toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)

    val possibleToCompleteInOneHour = (SIXTY * SIXTY) / averageTime
    val servicesNeededToCompleteAllHitsInOneHour = averageHitsPerHour / possibleToCompleteInOneHour

    println("$averageTime - Average Report Run Time")
    println("$possibleToCompleteInOneHour - Reports Completed By One Service In One Hour")

    println("--- Services ---")

    println("$servicesNeededToCompleteAllHitsInOneHour - Number Of Services Needed")
}