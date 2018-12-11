package ten

import java.util.*

object Ten {
    val points = ArrayList<TenPoint>()

    @JvmStatic
    fun main(ags: Array<String>) {
        println("Answer two: \t\t ${getAnswerOne()}")
    }

    private fun parseFile() {
        points.clear()

        val br = utils.FileUtils.loadFileAsBufferedReader("10.txt")

        br.lines().forEach { line ->
            points.add(parseLine(line))
        }
    }

    private fun parseLine(line: String): TenPoint {
        // position=< x, y> velocity=< x,y>
        // X: left = negative, right = positive
        // Y: up   = negative, down  = positive
        val parts = line.split("<", ",", ">")

        val x = parts[1].trim().toInt()
        val y = parts[2].trim().toInt()
        val xSpeed = parts[4].trim().toInt()
        val ySpeed = parts[5].trim().toInt()

        return TenPoint(x, y, xSpeed, ySpeed)
    }

    /**
     * Calculates the vertical distance between the lowest and highest point
     */
    private fun getHeight(): Int {
        var min = 999
        var max = -999

        points.forEach { point ->
            if (point.y > max) {
                max = point.y
            } else if (point.y < min) {
                min = point.y
            }
        }

        return Math.abs(max - min)
    }

    private fun getMinX(): Int {
        var min = 999

        points.forEach {
            if (it.x < min) {
                min = it.x
            }
        }

        return min
    }

    private fun getMaxX(): Int {
        var max = -999

        points.forEach {
            if (it.x > max) {
                max = it.x
            }
        }

        return max
    }

    private fun getMinY(): Int {
        var min = 999

        points.forEach {
            if (it.y < min) {
                min = it.y
            }
        }

        return min
    }

    private fun getMaxY(): Int {
        var max = -999

        points.forEach {
            if (it.y > max) {
                max = it.y
            }
        }

        return max
    }

    private fun movePoints() {
        points.forEach { it.move() }
    }

    private fun printPoints() {
        val minX = getMinX()
        val maxX = getMaxX()
        val minY = getMinY()
        val maxY = getMaxY()

        val unprintedPoints = points

        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                var found = false
                var foundPoint = TenPoint(0, 0, 0, 0)
                unprintedPoints.forEach { point ->
                    if (point.x == x && point.y == y && !found) {
                        print("#")
                        foundPoint = point
                        found = true
                    }
                }

                if (!found) {
                    print(".")
                    unprintedPoints.remove(foundPoint)
                }
            }
            println()
        }

    }

    private fun getAnswerOne(): Int {
        parseFile()
        var seconds = 0

        while (getHeight() != 9) {
            seconds++
            movePoints()
        }

        printPoints()

        return seconds
    }

}