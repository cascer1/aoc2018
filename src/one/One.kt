package one

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


object One {

    @JvmStatic
    fun main(args: Array<String>) {
        val answer = findAnswer()
        println("answer: $answer")
    }

    private fun findAnswer(): Int {
        val classLoader = javaClass.classLoader
        var file: InputStream

        var iteration = 0
        var answer = 0
        var duplicateFound = false
        val frequencies = HashSet<Int>()

        frequencies.add(0)

        while (!duplicateFound) {
            file = classLoader.getResource("01.txt")!!.openStream()
            var lineNumber = 1

            BufferedReader(InputStreamReader(file)).use { br ->
                br.forEachLine iterator@{ line ->
                    if (!duplicateFound) {
                        answer += Integer.valueOf(line)
                        println("Line ${lineNumber++} \t ($line) \t answer: $answer")

                        if (frequencies.contains(answer)) {
                            duplicateFound = true
                            return@iterator
                        } else {
                            frequencies.add(answer)
                        }
                    }
                }
            }

            println("Iteration ${iteration++} answer: $answer")
            println("Unique frequencies found: ${frequencies.size}")
            println("=== === === === === === === === ===")
        }

        return answer
    }
}
