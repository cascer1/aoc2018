import java.time.LocalDateTime

class FourEvent (var month: Int, var day: Int, var minute: Int, event: String)  {
    var guardId: Int = 0
    var eventType: FourEventType? = null
    var date: Int = 0

    init {
        date = month + day + minute
    }
}

