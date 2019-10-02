fun main() {
    println("Let's start parsing reporting metrics...")
    val inputHandler = InputHandler()
    val performanceMetricLines = inputHandler.read()

    for (line in performanceMetricLines) {
        println(line)
    }
}