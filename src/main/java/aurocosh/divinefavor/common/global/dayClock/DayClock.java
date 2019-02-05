package aurocosh.divinefavor.common.global.dayClock;

import aurocosh.divinefavor.common.lib.LoopedCounter;
import aurocosh.divinefavor.common.util.UtilDayTime;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.command.ICommand;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber
public class DayClock {
    private static final int TRACKED_DIMENSION_ID = 0;
    private static final int AFTER_COMMAND_SYNC = UtilDayTime.TICKS_IN_DAY - 1;
    private static final LoopedCounter SYNC_COUNTER = new LoopedCounter(UtilDayTime.TICKS_IN_DAY);
    private static final LoopedCounter DAY_TIME_COUNTER = new LoopedCounter(UtilDayTime.TICKS_IN_DAY);
    private static final LinkedList<DayTimeAlarm> alarms = new LinkedList<>();
    private static int nextId = 0;

    public static int getTime(){
        return DAY_TIME_COUNTER.getCount();
    }

    public static int addAlarm(int dayTimeInTicks, Runnable callback, boolean repeat) {
        DayTimeAlarm alarm = new DayTimeAlarmNormal(nextId++, dayTimeInTicks, repeat, callback);
        enqueueAlarm(alarm);
        return alarm.id;
    }

    public static int addAlarm(int dayTimeInTicks, ClockAlarmCallback callback, boolean repeat) {
        DayTimeAlarm alarm = new DayTimeAlarmId(nextId++, dayTimeInTicks, repeat, callback);
        enqueueAlarm(alarm);
        return alarm.id;
    }

    public static void removeAlarm(int id) {
        int index = UtilList.findIndex(alarms, alarm -> alarm.id == id);
        if (index != -1)
            alarms.remove(id);
    }

    @SubscribeEvent
    public static void serverTickEnd(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
            return;
        DAY_TIME_COUNTER.tick();
        if (SYNC_COUNTER.tick())
            syncTime();

        int time = DAY_TIME_COUNTER.getCount();
        processAlarms(time);
    }

    private static void syncTime() {
        WorldServer world = DimensionManager.getWorld(TRACKED_DIMENSION_ID);
        int time = UtilDayTime.getDayTime(world);
        DAY_TIME_COUNTER.setCount(time);

        alarms.sort(new DayTimeAlarmComparator());
        List<DayTimeAlarm> alarmsToMove = new ArrayList<>();
        while (alarms.peek() != null && alarms.peek().time < time)
            alarmsToMove.add(alarms.remove());
        alarms.addAll(alarmsToMove);
    }

    private static void processAlarms(int time) {
        List<DayTimeAlarm> alarmsToRepeat = new ArrayList<>();
        while (alarms.peek() != null && alarms.peek().time == time) {
            DayTimeAlarm alarm = alarms.remove();
            alarm.activate();
            if (alarm.repeat)
                alarmsToRepeat.add(alarm);
        }
//        System.out.println(time);
        alarms.addAll(alarmsToRepeat);
    }

    private static void enqueueAlarm(DayTimeAlarm alarm) {
        int index = UtilList.findIndex(alarms, existingAlarm -> existingAlarm.time >= alarm.time);
        if (index != -1)
            alarms.add(index, alarm);
        else
            alarms.add(alarm);
    }

    @SubscribeEvent
    public static void onCommand(CommandEvent event) {
        ICommand command = event.getCommand();
        String name = command.getName();
        if (name.equals("time"))
            SYNC_COUNTER.setCount(AFTER_COMMAND_SYNC);
    }
}
