package ten

class TenPoint(var x: Int, var y: Int, var xSpeed: Int, var ySpeed: Int) {
    public fun move() {
        x += xSpeed
        y += ySpeed
    }
}