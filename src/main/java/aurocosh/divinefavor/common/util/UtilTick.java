package aurocosh.divinefavor.common.util;

public class UtilTick {
    public static int minutesToTicks(float minutes){
        return (int) (1200 * minutes);
    }
    public static int secondsToTicks(float seconds){
        return (int) (20 * seconds);
    }
}
