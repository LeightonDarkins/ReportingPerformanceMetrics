import java.io.BufferedReader
import java.io.FileReader

class InputHandler() {
    var fileReader: FileReader? = null

    private val SITE_NAME_INDEX = 0
    private val REPORT_NAME_INDEX = 1
    private val TEMPALTE_NAME_INDEX = 2
    private val HITS_INDEX = 3
    private val TOTAL_TIME_INDEX = 4
    private val AVERAGE_TIME_INDEX = 5

    fun read(): ArrayList<PerformanceMetricLine> {
        val performanceMetricLines = ArrayList<PerformanceMetricLine>()

        try {
            var line: String?

            fileReader = FileReader("input.csv")

            var bufferedReader = BufferedReader(fileReader)

            bufferedReader.readLine()

            line = bufferedReader.readLine()

            while (line != null) {
                val tokens = line.split(",")

                if (tokens.isNotEmpty()) {
                    val performanceMetricLine = PerformanceMetricLine(
                        tokens[SITE_NAME_INDEX],
                        tokens[REPORT_NAME_INDEX],
                        tokens[TEMPALTE_NAME_INDEX],
                        tokens[HITS_INDEX].toInt(),
                        tokens[TOTAL_TIME_INDEX].toDouble(),
                        tokens[AVERAGE_TIME_INDEX].toDouble()
                    )

                    performanceMetricLines.add(performanceMetricLine)
                }

                line = bufferedReader.readLine()
            }
        } catch (e: Exception) {
            println("Error Reading File!")
            e.printStackTrace()
        }

        return performanceMetricLines
    }
}