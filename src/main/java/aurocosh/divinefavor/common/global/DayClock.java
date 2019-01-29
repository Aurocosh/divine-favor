package aurocosh.divinefavor.common.global;

import aurocosh.divinefavor.common.lib.TickCounter;
import aurocosh.divinefavor.common.util.UtilList;
import aurocosh.divinefavor.common.util.UtilDayTime;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber
public class DayClock {
    private static final boolean SYNC = true;
    private static final int TRACKED_DIMENSION_ID = 0;
    private static final TickCounter DAY_TIME_COUNTER = new TickCounter(UtilDayTime.TICKS_IN_DAY);
    private static final LinkedList<DayTimeAlarm> alarms = new LinkedList<>();
    private static int nextId = 0;

    public static int addAlarm(int dayTimeInTicks, Runnable callback, boolean repeat) {
        DayTimeAlarm alarm = new DayTimeAlarm(nextId++, dayTimeInTicks, repeat, callback);
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
        boolean dayEnded = DAY_TIME_COUNTER.tick();
        if (dayEnded && SYNC) {
            WorldServer world = DimensionManager.getWorld(TRACKED_DIMENSION_ID);
            int time = UtilDayTime.getDayTime(world);
            DAY_TIME_COUNTER.setCurrentTicks(time);
        }

        int time = DAY_TIME_COUNTER.getCurrentTicks();
        processAlarms(time);
    }

    private static void processAlarms(int time) {
        List<DayTimeAlarm> alarmsToRepeat = new ArrayList<>();
        while (alarmTriggered(alarms.peek(), time)) {
            DayTimeAlarm alarm = alarms.remove();
            alarm.callback.run();
            if (alarm.repeat)
                alarmsToRepeat.add(alarm);
        }
        alarms.addAll(alarmsToRepeat);
    }

    private static boolean alarmTriggered(DayTimeAlarm alarm, int time) {
        if (alarm == null)
            return false;
        return alarm.time <= time;
    }

    private static void enqueueAlarm(DayTimeAlarm alarm) {
        int index = UtilList.findIndex(alarms, existingAlarm -> existingAlarm.time >= alarm.time);
        if (index != -1)
            alarms.set(index, alarm);
        else
            alarms.add(alarm);
    }
}
