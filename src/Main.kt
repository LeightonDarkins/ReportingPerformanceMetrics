import java.math.RoundingMode

fun main() {
    val inputHandler = InputHandler()
    val performanceMetricLines = inputHandler.read()

    val grouped = performanceMetricLines.groupBy { it.siteName }

    val aggregates = grouped.map { (key, value) ->
        val hits = value.sumBy { line -> line.hits }
        val time = value.sumByDouble { line -> line.averageTime }

        AggregateMetricLine(key, hits, time)
    }

    val averageHits = aggregates.map { it.hits }.average().toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
    val averageTime = aggregates.map { it.averageTime }.average().toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)

    println("$averageHits Report Requests Per Month, Per Site")
    println("Average Report Runtime: $averageTime seconds")
}