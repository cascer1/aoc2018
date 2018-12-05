import java.io.BufferedReader
import java.io.InputStreamReader

object Five {

    @JvmStatic
    fun main(args: Array<String>) {
        val answerOne = findAnswerOne()
        println("answer 1: \t\t\t$answerOne")

        val answerTwo = findAnswerTwo()
        println("answer 2: \t\t\t$answerTwo")
    }

    private fun loadFile(): String {
        val classLoader = javaClass.classLoader
        val file = classLoader.getResource("five.txt")!!.openStream()

        BufferedReader(InputStreamReader(file)).use { br ->
            return br.readLine()
        }
    }

    private fun destroyFirstPair(input: String): String {

        (0 until input.length - 1).forEach { i ->
            if (input[i].toUpperCase() == input[i + 1].toUpperCase() && input[i] != input[i + 1]) {
                // found pair of same units with different polarisation
                return input.removeRange(i, i + 2)
            }
        }

        return input
    }

    private fun containsPairs(input: String): Boolean {
        (0 until input.length - 1).forEach { i ->
            if (input[i].toUpperCase() == input[i + 1].toUpperCase() && input[i] != input[i + 1]) {
                return true
            }
        }

        return false
    }

    private fun removeElement(input: String, letter: Char): String {
        return input.replace(letter.toString(), "", true)
    }

    private fun removeDuplicates(input: String): String {
        var polymer = input

        while (containsPairs(polymer)) {
            polymer = destroyFirstPair(polymer)
        }

        return polymer
    }


    private fun findAnswerOne(): Int {
        val input = loadFile()

        val polymer = removeDuplicates(input)

        return polymer.length
    }

    private fun findAnswerTwo(): Int {
        val input = loadFile()
        val alphabet = "abcdefghijklmnopqrstuvwxyz"

        var shortest = input.length

        alphabet.forEach { letter ->
            val polymer = removeElement(input, letter)

            val result = removeDuplicates(polymer)

            println("Removing $letter produces length ${result.length}")

            if (result.length < shortest) {
                shortest = result.length
            }
        }

        return shortest
    }
}