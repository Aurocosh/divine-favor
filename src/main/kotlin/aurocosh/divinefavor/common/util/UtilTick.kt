package aurocosh.divinefavor.common.util

object UtilTick {
    fun minutesToTicks(minutes: Float): Int {
        return (1200 * minutes).toInt()
    }

    fun secondsToTicks(seconds: Float): Int {
        return (20 * seconds).toInt()
    }
}
