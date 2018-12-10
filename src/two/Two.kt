package two

import java.io.BufferedReader
import java.io.InputStreamReader

object Two {

    @JvmStatic
    fun main(args: Array<String>) {
        val answer = findAnswerTwo()
        println()
        println("answer: \t\t\t$answer")
    }

    private fun findAnswerOne(): Int {
        val classLoader = javaClass.classLoader
        val file = classLoader.getResource("02.txt")!!.openStream()

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

    private fun findAnswerTwo(): String {
        val classLoader = javaClass.classLoader
        val file = classLoader.getResource("02.txt")!!.openStream()

        val lines = ArrayList<String>()

        BufferedReader(InputStreamReader(file)).use { br ->
            br.forEachLine { line ->
                lines.add(line)
            }
        }

        var lineOne = ""
        var lineTwo = ""
        var found = false

        (0 until lines.size).forEach outer@{ i ->
            val thisLine = lines[i]

            if (found) {
                return@outer
            }

            (i + 1 until lines.size).forEach inner@{ j ->
                val thatLine = lines[j]

                if (differsByOne(thisLine, thatLine)) {
                    lineOne = thisLine
                    lineTwo = thatLine
                    found = true
                    return@outer
                }
            }
        }

        println("lineOne = $lineOne")
        println("lineTwo = $lineTwo")

        return getShared(lineOne, lineTwo)
    }

    private fun differsByOne(string1: String, string2: String): Boolean {
        if (string1.length != string2.length) {
            return false
        }

        var mistakesAllowed = 1

        val arrayOne = string1.toCharArray()
        val arrayTwo = string2.toCharArray()

        for (i in 0 until string1.length - 1) {
            if (arrayOne[i] != arrayTwo[i]) {
                if (--mistakesAllowed < 0) {
                    return false
                }
            }
        }

        return true
    }

    private fun getShared(string1: String, string2: String): String {
        val arrayOne = string1.toCharArray()
        val arrayTwo = string2.toCharArray()

        val finalString = StringBuilder()

        (0 until string1.length).forEach { i ->
            if (arrayOne[i] == arrayTwo[i]) {
                finalString.append(arrayOne[i])
            }
        }

        return finalString.toString()
    }
}