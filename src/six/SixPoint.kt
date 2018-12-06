package six

class SixPoint(x: Int, y: Int, area: Int = 0, disqualified: Boolean = false) {

    val x = x
    val y = y
    var area = area
    var disqualified = disqualified
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