import java.io.BufferedReader
import java.io.InputStreamReader

object Three {

    @JvmStatic
    fun main(args: Array<String>) {
        val answer = findAnswerOne()
        println()
        println("answer: \t\t\t$answer")
    }

    private fun loadFile(): ArrayList<ThreeRectangle> {
        val classLoader = javaClass.classLoader
        val file = classLoader.getResource("three.txt")!!.openStream()

        val returned = ArrayList<ThreeRectangle>()

        BufferedReader(InputStreamReader(file)).use { br ->
            br.forEachLine { line ->
                returned.add(parseLine(line))
            }
        }

        return returned
    }

    private fun parseLine(line: String): ThreeRectangle {
        // #1 @ 7,589: 24x11

        val parts = line.split(" ")

        val rectangleId = parts[0].substring(1).toInt()
        val rectangleStart = parts[2].split(",")

        val rectangleStartX = rectangleStart[0].toInt()
        val rectangleStartY = rectangleStart[1].trimEnd(':').toInt()

        val rectangleSize = parts[3].split("x")

        val rectangleWidth = rectangleSize[0].toInt()
        val rectangleHeight = rectangleSize[1].toInt()

        return ThreeRectangle(rectangleId, rectangleStartX, rectangleStartY, rectangleWidth, rectangleHeight)
    }

    private fun buildClaimMap(rectangles: ArrayList<ThreeRectangle>): Array<Array<String>> {
        val squares = Array(1000) { Array(1000){ "." }  }

        for (rectangle in rectangles) {
            (rectangle.x until rectangle.x + rectangle.width).forEach{x ->
                (rectangle.y until rectangle.y + rectangle.height).forEach{y ->
                    when {
                        squares[x][y] == "." -> squares[x][y] = "${rectangle.id}"
                        else -> {
                            val squareClaims = ArrayList<String>(squares[x][y].split(",").toList())
                            squareClaims.add(rectangle.id.toString())
                            setClaimed(rectangles, squareClaims)
                            squares[x][y] = squareClaims.joinToString(",")
                        }
                    }
                }
            }
        }

        return squares
    }

    private fun setClaimed(rectangles: ArrayList<ThreeRectangle>, ids: List<String>) {
        for(rectangle in rectangles) {
            if(ids.contains(rectangle.id.toString())) {
                rectangle.hasOverlap = true
            }
        }
    }

    private fun getOverlapCount(claimMap: Array<Array<String>>): Int {
        var overlaps = 0

        (0 until claimMap.size).forEach{x ->
            val y = claimMap[x]
            (0 until y.size).forEach{
                if(claimMap[x][it] != "." && claimMap[x][it].contains(",")) {
                    overlaps++
                }
            }
        }

        return overlaps
    }

    private fun findAnswerOne(): Int {
        val rectangles = loadFile()
        val claimMap = buildClaimMap(rectangles)

        return getOverlapCount(claimMap)
    }

    private fun findAnswerTwo(): Int {
        val rectangles = loadFile()
        val claimMap = buildClaimMap(rectangles)

        for(rectangle in rectangles) {
            if(!rectangle.hasOverlap) {
                return rectangle.id
            }
        }

        return 0
    }
}