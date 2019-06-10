package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.lib.TimePeriod
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

object UtilDayTime {
    val TICKS_IN_HOUR = 1000
    val TICKS_IN_DAY = TICKS_IN_HOUR * 24
    val TICKS_IN_MINUTE = TICKS_IN_HOUR / 60

    fun hoursToTicks(hour: Int): Int {
        val hours = MathHelper.clamp(hour, 0, 24)
        return hours * TICKS_IN_HOUR
    }

    fun fromHours(start: Int, stop: Int): TimePeriod {
        return TimePeriod(hoursToTicks(start), hoursToTicks(stop))
    }

    fun clampDay(ticks: Int): Int {
        return MathHelper.clamp(ticks, 0, TICKS_IN_DAY)
    }

    fun getDayTime(world: World): Int {
        return (world.worldTime % TICKS_IN_DAY).toInt()
    }

    fun ticksBetween(fromDayTicks: Int, toDayTicks: Int): Int {
        val from = clampDay(fromDayTicks)
        val to = clampDay(toDayTicks)

        if(from < to)
            return to - from
        return TICKS_IN_DAY - to + from
    }
}
