package seven

import utils.FileUtils


object Seven {

    var steps = HashMap<Char, SevenStep>()
    var elves = HashMap<Char, Char>()
    var done = ArrayList<Char>()

    @JvmStatic
    fun main(ags: Array<String>) {
        println("Answer one: \t\t ${getAnswerOne()}")
        println()
        println("Answer two: \t\t ${getAnswerTwo()}")

    }


    private fun parseFile() {
        val reader = FileUtils.loadFileAsBufferedReader("seven.txt")

        steps.clear()
        elves.clear()
        done.clear()

        reader.forEachLine { line ->
            parseLine(line)
        }

        ('A'..'Z').forEach { letter ->
            if (!steps.containsKey(letter)) {
                steps[letter] = SevenStep(letter)
            }
        }
    }

    private fun parseLine(line: String) {
        // quick and dirty solution
        // Step Z must be finished before step E can begin.

        val parts = line.split(" ")
        val letter = parts[7].toCharArray()[0]
        val dependency = parts[1].toCharArray()[0]

        if (!steps.contains(letter)) {
            steps[letter] = SevenStep(letter)
        }

        steps[letter]!!.depends.add(dependency)
    }

    private fun performStep(letter: Char) {
        steps[letter]!!.done = true
        steps[letter]!!.running = false
        done.add(letter)

        steps.filter { (_, step) -> step.depends.contains(letter) && !step.done }.forEach { (_, step) ->
            step.depends.remove(letter)
        }
    }

    private fun createWorkers(count: Int) {
        (0 until count).forEach { number ->
            val workerLetter = (number + 65).toChar()

            elves[workerLetter] = '.'
        }
    }

    private fun setWorkerStatus(worker: Char, letter: Char) {
        steps[letter]!!.running = true
        elves[worker] = letter
    }

    private fun printWorkerStatusHeader() {
        print("SECOND")

        elves.forEach { (letter, _) ->
            print("\t$letter")
        }

        println()
    }

    private fun printWorkerStatus(second: Int) {
        print("$second\t")

        elves.forEach { (_, step) ->
            print("\t$step")
        }

        println()
    }

    private fun getAnswerOne(): String {
        parseFile()

        var remainingSteps = steps.filter { (_, step) -> !step.done && step.depends.size == 0 }

        while (remainingSteps.isNotEmpty()) {
            val nextLetter = remainingSteps.entries.first().key
            performStep(nextLetter)
            remainingSteps = steps.filter { (_, step) -> !step.done && step.depends.size == 0 }
        }

        val returned = StringBuilder()

        done.forEach { returned.append(it) }

        return returned.toString()
    }


    private fun getAnswerTwo(): Int {
        parseFile()
        createWorkers(5)
        var second = 0

        printWorkerStatusHeader()

        var remainingSteps = steps.filter { !it.value.done }

        while (remainingSteps.isNotEmpty()) {

            // Give idle elves a new job
            elves.filter { it.value == '.' }.forEach forEachElf@{ (elf, _) ->
                val availableSteps = remainingSteps.filter { it.value.depends.isEmpty() && !it.value.running }

                if (availableSteps.isEmpty()) {
                    return@forEachElf
                }

                val thisStep = availableSteps[availableSteps.keys.first()]!!

                thisStep.running = true
                elves[elf] = thisStep.letter
            }

            // Print status
            printWorkerStatus(second)

            // advance process of every running step
            val runningSteps = steps.filter { !it.value.done && it.value.running }

            runningSteps.forEach { (letter, step) ->
                step.timeRemaining--

                if (step.timeRemaining == 0) {
                    performStep(letter)

                    elves.forEach { (elfLetter, stepLetter) ->
                        if (stepLetter == letter) {
                            elves[elfLetter] = '.'
                        }
                    }
                }
            }

            second++
            remainingSteps = steps.filter { !it.value.done }
        }



        return second
    }
}