package six

class SixDot(x: Int, y: Int, area: Int = 0) {

    var x: Int = x
        set (value) {
            if (value < minX) {
                minX = value
            } else if (value > maxX) {
                maxX = value
            }
        }

    var y: Int = y
        set (value) {
            if (value < minY) {
                minY = value
            } else if (value > maxY) {
                maxY = value
            }
        }


    companion object {
        var maxX = 0
        var minX = 0
        var maxY = 0
        var minY = 0
    }
}