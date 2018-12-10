package four

import java.io.BufferedReader
import java.io.InputStreamReader

object Four {

    @JvmStatic
    fun main(args: Array<String>) {
        val answerOne = findAnswerOne()
        val answerTwo = findAnswerTwo()
        println()
        println("answer 1: \t\t\t$answerOne")
        println("answer 2: \t\t\t$answerTwo")
    }

    private fun loadFile(): Array<FourEvent> {
        val classLoader = javaClass.classLoader
        val file = classLoader.getResource("04.txt")!!.openStream()

        val returned: List<FourEvent>
        returned = ArrayList()

        BufferedReader(InputStreamReader(file)).use { br ->
            br.forEachLine { line ->
                returned.add(FourEvent(line))
            }
        }

        return returned.toTypedArray()
    }

    private fun addGuardIds(events: Array<FourEvent>) {
        (0 until events.size).forEach { i ->
            val currentEvent = events[i]

            if (currentEvent.guardId == 0) {
                currentEvent.guardId = findCurrentGuard(events, i)
            }
        }
    }

    private fun findCurrentGuard(events: Array<FourEvent>, index: Int): Int {
        (index downTo 0).forEach { i ->
            if (events[i].eventType == FourEventType.START && events[i].guardId != 0) {
                return events[i].guardId
            }
        }

        return 0
    }

    private fun countGuardMinutes(guardMinutes: HashMap<Int, Array<Int>>, events: Array<FourEvent>) {
        var sleepStart = 0
        events.forEach { event ->
            if (event.eventType == FourEventType.SLEEP) {
                sleepStart = event.minute

                if (!guardMinutes.containsKey(event.guardId)) {
                    guardMinutes[event.guardId] = Array(60) { 0 }
                }
            }

            if (event.eventType == FourEventType.WAKE) {
                (sleepStart until event.minute).forEach {
                    guardMinutes[event.guardId]!![it]++
                }
            }
        }
    }

    private fun getGuardWithMostMinutes(guardMinutes: HashMap<Int, Array<Int>>): Int {
        var highest = 0
        var highestGuard = 0

        guardMinutes.forEach { guardId, minutes ->

            var totalMinutes = 0

            minutes.forEach { minute ->
                totalMinutes += minute
            }

            if (totalMinutes > highest) {
                highest = totalMinutes
                highestGuard = guardId
            }
        }

        return highestGuard
    }

    private fun getMostMinutesForGuard(guardMinutes: HashMap<Int, Array<Int>>, guardId: Int): Int {
        var highest = 0
        var highestMinute = 0

        val minutes = guardMinutes.get(guardId)!!

        (0 until minutes.size).forEach { i ->
            if (minutes[i] > highest) {
                highest = minutes[i]
                highestMinute = i
            }
        }

        return highestMinute
    }

    private fun getHighestGuardMinute(guardMInutes: HashMap<Int, Array<Int>>): Array<Int> {

        var highest = 0

        val returned = Array(2) { 0 } // 0 = guard, 1 = minute

        guardMInutes.forEach { guardId, minutes ->
            var currentHighest = 0
            var currentHighestMinute = 0

            (0 until minutes.size).forEach { i ->
                if (minutes[i] > currentHighest) {
                    currentHighest = minutes[i]
                    currentHighestMinute = i
                }
            }

            if (currentHighest > highest) {
                highest = currentHighest
                returned[0] = guardId
                returned[1] = currentHighestMinute
            }
        }

        return returned
    }

    private fun findAnswerOne(): Int {
        val events = loadFile()
        events.sortBy { it.date }
        addGuardIds(events)

        val guardMinutes = HashMap<Int, Array<Int>>()

        countGuardMinutes(guardMinutes, events)

        val highestGuard = getGuardWithMostMinutes(guardMinutes)
        val highestMinute = getMostMinutesForGuard(guardMinutes, highestGuard)

        return highestGuard * highestMinute
    }

    private fun findAnswerTwo(): Int {
        val events = loadFile()
        events.sortBy { it.date }
        addGuardIds(events)

        val guardMinutes = HashMap<Int, Array<Int>>()

        countGuardMinutes(guardMinutes, events)

        val highest = getHighestGuardMinute(guardMinutes)

        return highest[0] * highest[1]
    }
}