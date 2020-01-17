import java.math.BigDecimal
import java.math.RoundingMode

class MonthlyMetricLine(
    private val fileName: String,
    private val sitesUsingReports: BigDecimal,
    private val totalHits: Int,
    private val hitsPerMonthPerSite: BigDecimal,
    private val averageReportRuntime: BigDecimal
) : Line {

    private var averageHitsPerHour = BigDecimal(0)
    private var averageHitsPerDay = BigDecimal(0)
    private var averageHitsPerDayPerClinic = BigDecimal(0)
    private var possibleToCompleteInOneHour = BigDecimal(0)
    private var servicesNeededToCompleteAllHitsInOneHour = BigDecimal(0)
    private var ruleOfThumbValue = BigDecimal(0)

    init {
        averageHitsPerDayPerClinic = hitsPerMonthPerSite / AVERAGE_MONTH_LENGTH
        averageHitsPerDay = averageHitsPerDayPerClinic * sitesUsingReports
        averageHitsPerHour = averageHitsPerDay / AVERAGE_DAY_LENGTH
        possibleToCompleteInOneHour = (SIXTY * SIXTY) / (averageReportRuntime + QUEUE_TIME)
        servicesNeededToCompleteAllHitsInOneHour = averageHitsPerHour / possibleToCompleteInOneHour

        ruleOfThumbValue = (averageHitsPerHour * (averageReportRuntime + QUEUE_TIME)) / SIXTY
    }

    fun print() = println(getPrintContents())

    override fun getPrintContents() =
        "$fileName, $sitesUsingReports, $totalHits, $hitsPerMonthPerSite, $averageHitsPerDayPerClinic, $averageHitsPerDay, $averageHitsPerHour, $averageReportRuntime, $ruleOfThumbValue"

    fun printHeaders() = println(getHeaders())

    override fun getHeaders() =
        "filename, sites, totalHitsPerMonth, hitsPerMonthPerSite, hitsPerSitePerDay, hitsPerDay, hitsPerHour, reportRuntime, servicesRequired"

    override fun printRequirements() =
        println("${ruleOfThumbValue.setScale(0, RoundingMode.FLOOR)}\t\t services for $fileName")

    fun verbosePrint() {
        println("\n\n\n\n--- $fileName ---")

        println("\n--- Sites ---")

        println("$sitesUsingReports Sites Using Reports")

        println("\n--- Total Hits ---")

        println("$totalHits Total Hits")

        println("\n--- Hits ---")

        println("$hitsPerMonthPerSite - Report Hits Per Month, Per Site")

        println("$averageHitsPerDayPerClinic - Average Hits Per Day, Per Site")
        println("$averageHitsPerDay - Average Hits Per Day, Overall")
        println("$averageHitsPerHour - Average Hits Per Hour, Per Work Day, Overall")


        println("${averageReportRuntime}s - Average Report Run Time")
        println("${averageReportRuntime + QUEUE_TIME}s - Average Report Run Time (including ${QUEUE_TIME}s queue time)")
        println("$possibleToCompleteInOneHour - Reports Completed By One Service In One Hour")

        println("\n--- Services ---")

        println("$servicesNeededToCompleteAllHitsInOneHour - Number Of Services Needed")

        println("\n--- Rule Of Thumb (N*r)/T ---")

        println("$ruleOfThumbValue Services Needed")
    }
}