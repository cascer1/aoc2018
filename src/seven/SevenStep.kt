package seven


class SevenStep(var letter: Char) {
    var depends = HashSet<Char>()
    var done = false
    var timeRemaining = 0
    var running = false

    init {
        timeRemaining = 60 + letter.toInt() - 64
    }
}