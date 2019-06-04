package aurocosh.divinefavor.common.global.dayClock

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.LoopedCounter
import aurocosh.divinefavor.common.lib.extensions.firstIndex
import aurocosh.divinefavor.common.util.UtilDayTime
import net.minecraftforge.common.DimensionManager
import net.minecraftforge.event.CommandEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.*

@Mod.EventBusSubscriber(modid = DivineFavor.MOD_ID)
object DayClock {
    private val TRACKED_DIMENSION_ID = 0
    private val AFTER_COMMAND_SYNC = UtilDayTime.TICKS_IN_DAY - 1
    private val SYNC_COUNTER = LoopedCounter(UtilDayTime.TICKS_IN_DAY)
    private val DAY_TIME_COUNTER = LoopedCounter(UtilDayTime.TICKS_IN_DAY)
    private val alarms = LinkedList<DayTimeAlarm>()
    private var nextId = 0

    val time: Int
        get() = DAY_TIME_COUNTER.count

    fun addAlarm(dayTimeInTicks: Int, callback: () -> Unit, repeat: Boolean): Int {
        val alarm = DayTimeAlarmNormal(nextId++, dayTimeInTicks, repeat, callback)
        enqueueAlarm(alarm)
        return alarm.id
    }

    fun addAlarm(dayTimeInTicks: Int, callback: ClockAlarmCallback, repeat: Boolean): Int {
        val alarm = DayTimeAlarmId(nextId++, dayTimeInTicks, repeat, callback)
        enqueueAlarm(alarm)
        return alarm.id
    }

    fun removeAlarm(id: Int) {
        val index = alarms.firstIndex { alarm -> alarm.id == id }
        if (index != -1)
            alarms.removeAt(index)
    }

    @SubscribeEvent
    fun serverTickEnd(event: TickEvent.ServerTickEvent) {
        if (event.phase == TickEvent.Phase.END)
            return
        DAY_TIME_COUNTER.tick()
        if (SYNC_COUNTER.tick())
            syncTime()

        val time = DAY_TIME_COUNTER.count
        processAlarms(time)
    }

    private fun syncTime() {
        val world = DimensionManager.getWorld(TRACKED_DIMENSION_ID)
        val time = UtilDayTime.getDayTime(world)
        DAY_TIME_COUNTER.count = time

        alarms.sortWith(DayTimeAlarmComparator())
        val alarmsToMove = ArrayList<DayTimeAlarm>()
        while (alarms.peek() != null && alarms.peek().time < time)
            alarmsToMove.add(alarms.remove())
        alarms.addAll(alarmsToMove)
    }

    private fun processAlarms(time: Int) {
        val alarmsToRepeat = ArrayList<DayTimeAlarm>()
        while (alarms.peek() != null && alarms.peek().time == time) {
            val alarm = alarms.remove()
            alarm.activate()
            if (alarm.repeat)
                alarmsToRepeat.add(alarm)
        }
        //        System.out.println(time);
        alarms.addAll(alarmsToRepeat)
    }

    private fun enqueueAlarm(alarm: DayTimeAlarm) {
        val index = alarms.firstIndex { existingAlarm -> existingAlarm.time >= alarm.time }
        if (index != -1)
            alarms.add(index, alarm)
        else
            alarms.add(alarm)
    }

    @SubscribeEvent
    fun onCommand(event: CommandEvent) {
        val command = event.command
        val name = command.name
        if (name == "time")
            SYNC_COUNTER.count = AFTER_COMMAND_SYNC
    }
}
