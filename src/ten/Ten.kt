package ten

object Ten {
    val points = ArrayList<TenPoint>()

    @JvmStatic
    fun main(ags: Array<String>) {
        println("Answer one: \t\t ${getAnswerOne()}")
        println()
        println("Answer two: \t\t ${getAnswerTwo()}")
    }

    private fun parseFile() {
        points.clear()

        val br = utils.FileUtils.loadFileAsBufferedReader("10_small.txt")

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

        return TenPoint(x,y,xSpeed,ySpeed)
    }

    private fun getAnswerOne(): Int {
        parseFile()
        return 0
    }

    private fun getAnswerTwo(): Int {
        return 0
    }

}