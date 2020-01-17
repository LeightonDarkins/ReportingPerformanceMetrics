import java.math.BigDecimal
import java.math.RoundingMode

val AVERAGE_MONTH_LENGTH = BigDecimal(30)
val AVERAGE_DAY_LENGTH = BigDecimal(10)
val QUEUE_TIME = BigDecimal(5)
val SIXTY = BigDecimal(60)

fun main() {
    val fileNames = listOf(
        "january-2018",
        "february-2018",
        "march-2018",
        "april-2018",
        "may-2018",
        "june-2018",
        "july-2018",
        "august-2018",
        "september-2018",
        "october-2018",
        "november-2018",
        "december-2018",
        "january-2019",
        "february-2019",
        "march-2019",
        "april-2019",
        "may-2019",
        "june-2019",
        "july-2019",
        "august-2019",
        "september-2019",
        "october-2019",
        "december-2019")

    val results = ArrayList<Line>()
    val completionTimes = ArrayList<Line>()

    for (fileName in fileNames) {
        val inputHandler = InputHandler()
        val performanceMetricLines = inputHandler.read(fileName)
        val size = performanceMetricLines.size

        val grouped = performanceMetricLines.groupBy { it.siteName }

        var totalHits = 0

        val aggregateMetricLines = grouped.map { (key, value) ->
            val hits = value.sumBy { line -> line.hits }
            totalHits += hits

            val time = value.sumByDouble { line -> line.averageTime }

            AggregateMetricLine(key, hits, time)
        }

        val averageHitsPerMonth = aggregateMetricLines.map { it.hits }.average().toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)

        val averageTime = aggregateMetricLines.map { it.averageTime }.average().toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)

        val completionTimeLine = CompletionTimeLine(fileName, performanceMetricLines)

//        completionTimeLine.printHeaders()
//        completionTimeLine.printPercentages()
////
//        println()

        completionTimes.add(completionTimeLine)

        results.add(
            MonthlyMetricLine(
                fileName,
                aggregateMetricLines.size.toBigDecimal(),
                totalHits,
                averageHitsPerMonth,
                averageTime)
        )
    }

    println()

    results.forEach { it.printRequirements() }

    println()
//
//    for ((index, result) in results.withIndex()) {
//        if (index == 0) result.printHeaders()
//
//        result.print()
//    }
//
    val outputHandler = OutputHandler()

    outputHandler.write("results", results)
    outputHandler.write("completionTimes", completionTimes)
}

fun toPercentage(value: Double, setSize: Int) = BigDecimal((value / setSize) * 100).setScale(4, RoundingMode.HALF_EVEN)