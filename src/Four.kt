import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector

object Four {

    @JvmStatic
    fun main(args: Array<String>) {
        val answerOne = findAnswerOne()
//        val answerTwo = findAnswerTwo()
        println()
        println("answer 1: \t\t\t$answerOne")
//        println("answer 2: \t\t\t$answerTwo")
    }

    private fun loadFile(): Array<FourEvent> {
        val classLoader = javaClass.classLoader
        val file = classLoader.getResource("four.txt")!!.openStream()

        val returned: List<FourEvent>
        returned = ArrayList()

        BufferedReader(InputStreamReader(file)).use { br ->
            br.forEachLine { line ->
                returned.add(parseLine(line))
            }
        }

        return returned.toTypedArray()
    }

    private fun parseLine(line: String): FourEvent {
        // [1518-04-12 00:04] Guard #1193 begins shift
        // [1518-08-11 00:48] wakes up
        // [1518-07-12 00:15] falls asleep

        val timePattern = ".[0-9]{4}-([0-9]{2})-([0-9]{2}) [0-9]{2}:([0-9]{2}).*"

        val timeRegex = Regex(timePattern)
        val groups = timeRegex.matchEntire(line)!!.groupValues

        val month = groups[1].toInt() * 10000
        val day = groups[2].toInt() * 100
        val minute = groups[3].toInt()

        val event = FourEvent(month, day, minute, line)

        event.eventType = when {
            line.contains("Guard") -> {
                FourEventType.START
            }
            line.contains("wakes") -> {
                FourEventType.WAKE
            }
            line.contains("falls") -> {
                FourEventType.SLEEP
            }
            else -> {
                FourEventType.UNKNOWN
            }
        }

        if(event.eventType == FourEventType.START) {
            val guardStart = line.indexOf('#') + 1
            val guardEnd = line.indexOf(" ", guardStart)
            val guardId = line.substring(guardStart, guardEnd).toInt()
            event.guardId = guardId
        }

        return event
    }

    private fun addGuardIds(events: Array<FourEvent>) {
        (0 until events.size).forEach{i ->
            val currentEvent = events[i]

            if(currentEvent.guardId == 0) {
                currentEvent.guardId = findCurrentGuard(events, i)
            }
        }
    }

    private fun findCurrentGuard(events: Array<FourEvent>, index: Int): Int {
        (index downTo 0).forEach{i ->
            if(events[i].eventType == FourEventType.START && events[i].guardId != 0) {
                return events[i].guardId
            }
        }

        return 0
    }

    private fun findAnswerOne(): Int {
        val events = loadFile()
        events.sortBy { it.date }
        addGuardIds(events)

        return 0
    }
}