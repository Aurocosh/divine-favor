package aurocosh.divinefavor.common.global.dayClock;

@FunctionalInterface
public interface ClockAlarmCallback {
    void run(int alarmId);
}
