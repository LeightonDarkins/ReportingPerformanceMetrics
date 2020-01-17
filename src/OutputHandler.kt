import java.io.File

class OutputHandler {
    fun write(fileName:String, itemsToWrite: ArrayList<Line>) {
        try {
            val printWriter = File("resources/$fileName.csv").printWriter()

            for ((index, item) in itemsToWrite.withIndex()) {
                if (index == 0) printWriter.write(item.getHeaders() + "\n")

                printWriter.write(item.getPrintContents() + "\n")
            }

            printWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}