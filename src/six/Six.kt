package six

import java.io.BufferedReader
import java.io.InputStreamReader


object Six {

    @JvmStatic
    fun main(args: Array<String>) {
        val answerOne = findAnswerOne()
        println("answer 1: \t\t\t$answerOne")

        val answerTwo = findAnswerTwo()
        println("answer 2: \t\t\t$answerTwo")
    }

    private fun loadFile(): ArrayList<SixPoint> {
        val classLoader = javaClass.classLoader
        val file = classLoader.getResource("06.txt")!!.openStream()

        val returned = ArrayList<SixPoint>()

        BufferedReader(InputStreamReader(file)).use { br ->
            br.forEachLine { line ->
                returned.add(parsePoint(line))
            }
        }

        return returned
    }

    private fun parsePoint(line: String): SixPoint {
        val coordinates = line.split(",")

        val x = coordinates[0].trim().toInt()
        val y = coordinates[1].trim().toInt()

        return SixPoint(x, y)
    }

    private fun calculateDistance(point: SixPoint, x: Int, y: Int): Int {
        return Math.abs(point.x - x) + Math.abs(point.y - y)
    }

    private fun findAnswerOne(): Int {
        val points = loadFile()

        println("x: ${SixPoint.minX} .. ${SixPoint.maxX}")
        println("y: ${SixPoint.minY} .. ${SixPoint.maxY}")

        (0..SixPoint.maxY).forEach { y ->
            println()
            (0..SixPoint.maxX).forEach x@{ x ->
                var shortestDistance = 999
                var nearestPoint = points[0]
                val distances = ArrayList<Int>()

                var exact = false

                points.forEach { point ->

                    val distance = calculateDistance(point, x, y)
                    distances.add(distance)

                    if (distance < shortestDistance) {
                        shortestDistance = distance
                        nearestPoint = point
                    }

                    if (point.x == x && point.y == y) {
                        print(point.letter)
                        exact = true
                    }
                }

                if (x >= SixPoint.maxX || x <= SixPoint.minX || y >= SixPoint.maxY || y <= SixPoint.minY) {
                    nearestPoint.disqualified = true
                }

                val sorted = distances.sorted()


                if (sorted[0] == sorted[1]) {
                    print("@")
                    return@x
                }

                if (!exact) {
                    print(nearestPoint.letter)
                }

                nearestPoint.area++


            }
        }
        println()

        val validPoints = points.filter { !it.disqualified }.sortedBy { it.area }

        validPoints.forEach { point ->
            println("[${point.x}, ${point.y}] (${point.letter}) has totalDistance of ${point.area}")
        }

        return validPoints.last().area
    }

    private fun findAnswerTwo(): Int {

        val points = loadFile()

        var validDotCount = 0

        (SixPoint.minY..SixPoint.maxY).forEach { y ->
            println()
            (SixPoint.minX..SixPoint.maxX).forEach { x ->
                var totalDistance = 0

                points.forEach { point ->
                    totalDistance += calculateDistance(point, x, y)
                }

                if (totalDistance < 10000) {
                    print("#")
                    validDotCount++
                } else {
                    print(".")
                }
            }
        }
        println()
        return validDotCount
    }
}