package six

class SixPoint(val x: Int, val y: Int, var area: Int = 0, var disqualified: Boolean = false) {

    var letter = 'a'

    init {
        if (x < minX) {
            minX = x
        } else if (x > maxX) {
            maxX = x
        }

        if (y < minY) {
            minY = y
        } else if (y > maxY) {
            maxY = y
        }

        letter = nextLetter++
    }

    companion object {
        var maxX = 0
        var minX = 999
        var maxY = 0
        var minY = 999
        var nextLetter = 65.toChar()
    }
}

