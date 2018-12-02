import java.io.BufferedReader
import java.io.InputStreamReader

object Two {

    @JvmStatic
    fun main(args: Array<String>) {
        val answer = findAnswerOne()
        println()
        println("answer: \t\t\t$answer")
    }

    private fun findAnswerOne(): Int {
        val classLoader = javaClass.classLoader
        val file = classLoader.getResource("two.txt")!!.openStream()

        var amountTwo = 0 // Amount of IDs that contain two of the same letter
        var amountThree = 0 // Amount of IDs that contain three of the same letter
        var lineNumber = 1

        BufferedReader(InputStreamReader(file)).use { br ->
            br.forEachLine { line ->
                val uniqueCharacters = HashMap<Char, Int>()
                val lineChars: CharSequence = line

                lineChars.forEach { character ->
                    if (uniqueCharacters.containsKey(character)) {
                        val charCount = uniqueCharacters[character]!!
                        uniqueCharacters[character] = charCount + 1
                    } else {
                        uniqueCharacters[character] = 1
                    }
                }

                println("Line ${lineNumber++}")
                println(line)

                var twoFound = false
                var threeFound = false

                uniqueCharacters.forEach { character, count ->
                    println("Found character \t [$character] $count times")

                    if (count == 2 && !twoFound) {
                        amountTwo++
                        twoFound = true
                        println("\t\tFOUND DOUBLE CHAR")
                    }

                    if (count == 3 && !threeFound) {
                        amountThree++
                        threeFound = true
                        println("\t\tFOUND TRIPLE CHAR")
                    }
                }

                println("=== === === === === === === === ===")

            }
        }

        println("Double chars found: $amountTwo")
        println("Triple chars found: $amountThree")


        return amountTwo * amountThree
    }
}