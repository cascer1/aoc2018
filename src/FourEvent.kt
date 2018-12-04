class FourEvent(var event: String) {
    var guardId: Int = 0
        set(value) {
            if (!guardIds.contains(value)) {
                guardIds.add(value)
            }
            field = value
        }
    var eventType: FourEventType? = null

    var month = 0
    var day = 0
    var minute = 0
    var date: String = ""

    init {
        determineDate()
        determineType()
        parseGuard()
    }

    private fun determineDate() {
        val timePattern = ".[0-9]{4}-([0-9]{2})-([0-9]{2}) [0-9]{2}:([0-9]{2}).*"

        val timeRegex = Regex(timePattern)
        val groups = timeRegex.matchEntire(event)!!.groupValues

        month = groups[1].toInt()
        day = groups[2].toInt()
        minute = groups[3].toInt()

        date = event.split("]")[0] + "]"
    }

    private fun determineType() {
        eventType = when {
            event.contains("begins") -> FourEventType.START
            event.contains("wakes") -> FourEventType.WAKE
            else -> FourEventType.SLEEP
        }
    }

    private fun parseGuard() {
        if(eventType != FourEventType.START) {
            return
        }

        val guardStart = event.indexOf('#') + 1
        val guardEnd = event.indexOf(" ", guardStart)
        guardId = event.substring(guardStart, guardEnd).toInt()
    }

    companion object {
        val guardIds = ArrayList<Int>()
    }
}

