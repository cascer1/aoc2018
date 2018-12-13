package twelve

import utils.FileUtils
import java.util.*

object Twelve {

    var plants = HashMap<Long, Boolean>()
    val options = ArrayList<ArrayList<Boolean>>()
    var maxPlant = 0L
    var minPlant = -2L

    @JvmStatic
    fun main(args: Array<String>) {
        println("Answer one: ${getAnswerOne()}")
//        println("Answer two: ${getAnswerTwo()}")
    }

    private fun parseFile() {
        plants.clear()
        options.clear()

        val entireFile = FileUtils.loadFileAsBufferedReader("12.txt")
        var lineNumber = 0

        entireFile.forEachLine { line ->
            if (lineNumber == 0) {
                // get plant setup
                var i = 0L
                val plantLine = line.split(" ")[2]

                plants[-2] = false
                plants[-1] = false

                plantLine.forEach { plant ->
                    plants[i] = (plant == '#')
                    maxPlant = i++
                }

                // Add some buffer plants to the right
                (0..5).forEach {
                    plants[i] = false
                    maxPlant = i++
                }
            } else if (lineNumber == 1) {
                // skip
            } else {
                // Load options for growth
                val thisList = ArrayList<Boolean>(5)
                if (line[9] == '#') {
                    (0..4).forEach {
                        //                    thisList[it] = line[it] == '#'
                        thisList.add(line[it] == '#')
                    }

                    options.add(thisList)
                }
            }
            lineNumber++
        }
    }

    private fun printState(generation: Int) {
        print(generation)
        print("\t")

        plants.forEach { (_, grown) ->
            if (grown) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }

    private fun calculateNextPlant(id: Long): Boolean {
        var minusOne = false
        var minusTwo = false
        var plusOne = false
        var plusTwo = false

        if (id >= minPlant + 1) {
            minusOne = plants[id - 1]!!
        }

        if (id >= minPlant + 2) {
            minusTwo = plants[id - 2]!!
        }

        if (id < plants.size - 4) {
            plusTwo = plants[id + 2]!!
        }

        if (id < plants.size - 3) {
            plusOne = plants[id + 1]!!
        }

        if (!minusTwo && !minusOne && !plants[id]!! && !plusOne && !plusTwo) {
            return false
        }

        options.forEach { option ->
            if (option[0] == minusTwo
                && option[1] == minusOne
                && option[2] == plants[id]!!
                && option[3] == plusOne
                && option[4] == plusTwo
            ) {
                return true
            }
        }

        return false
    }

    private fun calculateNextGeneration() {
        val newGeneration = HashMap<Long, Boolean>()

        var mustIncreaseMax = false
        (0..3).forEach { offset ->
            if (plants[maxPlant - offset]!!) {
                mustIncreaseMax = true
            }
        }

        if (mustIncreaseMax) {
            plants[++maxPlant] = false
        }

        (minPlant..maxPlant).forEach { id ->
            newGeneration[id] = calculateNextPlant(id)
        }

//        plants.forEach { id, _ ->
//            newGeneration[id] = calculateNextPlant(id)
//        }

        plants = newGeneration
    }

    private fun calculateSum(): Long {
        var potSum = 0L

        plants.forEach { (id, status) ->
            if (status) {
                potSum += id
            }
        }

        return potSum
    }

    private fun getAnswerOne(): Long {
        parseFile()
        printState(0)

        (1..20).forEach { generation ->
//            adjustMin()
            calculateNextGeneration()
            printState(generation)
        }

        return calculateSum()
    }

    private fun adjustMin() {
        (minPlant..minPlant + 5).forEach { id ->
            if (!plants[id]!! &&
                !plants[id + 1]!! &&
                !plants[id + 2]!! &&
                !plants[id + 3]!!
            ) {
                minPlant++
            }
        }
    }

    private fun getAnswerTwo(): Long {
        parseFile()

        (1..50000000000).forEach { generation ->
            adjustMin()
            if (generation % 1000 == 0L) {
                print("Generation $generation")
                println("\t ${50000000000 - generation} remaining")
            }

            calculateNextGeneration()
        }

        return calculateSum()
    }

}