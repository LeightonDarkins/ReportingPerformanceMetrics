import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

class CompletionTimeLine(
    val fileName: String,
    val performanceMetricLines: List<PerformanceMetricLine>
) : Line {
    private var fasterThanASecond = 0.0
    private var fasterThan10Seconds = 0.0
    private var fasterThan30Seconds = 0.0
    private var fasterThan60Seconds = 0.0
    private var fasterThan120Seconds = 0.0
    private var fasterThan240Seconds = 0.0
    private var fasterThan480Seconds = 0.0
    private var fasterThan600Seconds = 0.0
    private var slowerThan600Seconds = 0.0

    init {
        fasterThanASecond = performanceMetricLines.filter { it.averageTime <= 1 }.size.toDouble()
        fasterThan10Seconds = performanceMetricLines.filter { it.averageTime > 1 && it.averageTime <= 10 }.size.toDouble()
        fasterThan30Seconds = performanceMetricLines.filter { it.averageTime > 10 && it.averageTime <= 30 }.size.toDouble()
        fasterThan60Seconds = performanceMetricLines.filter { it.averageTime > 30 && it.averageTime <= 60 }.size.toDouble()
        fasterThan120Seconds = performanceMetricLines.filter { it.averageTime > 60 && it.averageTime <= 120 }.size.toDouble()
        fasterThan240Seconds = performanceMetricLines.filter { it.averageTime > 120 && it.averageTime <= 240 }.size.toDouble()
        fasterThan480Seconds = performanceMetricLines.filter { it.averageTime > 240 && it.averageTime <= 480 }.size.toDouble()
        fasterThan600Seconds = performanceMetricLines.filter { it.averageTime > 480 && it.averageTime <= 600 }.size.toDouble()
        slowerThan600Seconds = performanceMetricLines.filter { it.averageTime > 699 }.size.toDouble()
    }

    override fun printRequirements() {}

    fun print() = println(getPrintContents())

    override fun getPrintContents() = "$fileName, ${toPercentage(fasterThanASecond)}, ${toPercentage(fasterThan10Seconds)}, ${toPercentage(fasterThan30Seconds)}, ${toPercentage(fasterThan60Seconds)}, ${toPercentage(fasterThan120Seconds)}, ${toPercentage(fasterThan240Seconds)}, ${toPercentage(fasterThan480Seconds)}, ${toPercentage(fasterThan600Seconds)}, ${toPercentage(slowerThan600Seconds)}"

    fun printHeaders() = println(getHeaders())

    override fun getHeaders() = "fileName, fasterThanASecond, fasterThan10Seconds, fasterThan30Seconds, fasterThan60Seconds, fasterThan120Seconds, fasterThan240Seconds, fasterThan480Seconds, fasterThan600Seconds, slowerThan600Seconds"

    fun printVerbose() {
        val size = performanceMetricLines.size
        val sorted = performanceMetricLines.sortedBy { it.averageTime }

        println(fileName)

        println("\t1s : $fasterThanASecond (${toPercentage(fasterThanASecond, size)}%)")
        println("\t10s: $fasterThan10Seconds (${toPercentage(fasterThan10Seconds, size)}%)")
        println("\t30s: $fasterThan30Seconds (${toPercentage(fasterThan30Seconds, size)}%)")
        println("\t60s: $fasterThan60Seconds (${toPercentage(fasterThan60Seconds, size)}%)")
        println("\t120s: $fasterThan120Seconds (${toPercentage(fasterThan120Seconds, size)}%)")
        println("\t240s: $fasterThan240Seconds (${toPercentage(fasterThan240Seconds, size)}%)")
        println("\t480s: $fasterThan480Seconds (${toPercentage(fasterThan480Seconds, size)}%)")
        println("\t600s: $fasterThan600Seconds (${toPercentage(fasterThan600Seconds, size)}%)")
        println("\t600+: $slowerThan600Seconds (${toPercentage(slowerThan600Seconds, size)}%)")
        println("\tFastest: ${sorted.first().averageTime.roundToInt()}")
        println("\tSlowest: ${sorted.last().averageTime.roundToInt()}")
    }

    fun printPercentages() = println("$fileName, ${toPercentage(fasterThanASecond)}, ${toPercentage(fasterThan10Seconds)}, ${toPercentage(fasterThan30Seconds)}, ${toPercentage(fasterThan60Seconds)}, ${toPercentage(fasterThan120Seconds)}, ${toPercentage(fasterThan240Seconds)}, ${toPercentage(fasterThan480Seconds)}, ${toPercentage(fasterThan600Seconds)}, ${toPercentage(slowerThan600Seconds)}")

    fun toPercentage(value: Double) = "${BigDecimal((value / performanceMetricLines.size) * 100).setScale(2, RoundingMode.HALF_EVEN)}%"
}