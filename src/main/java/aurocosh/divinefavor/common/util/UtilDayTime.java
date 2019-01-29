package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.lib.TimePeriod;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class UtilDayTime {
    public static final int TICKS_IN_HOUR = 1000;
    public static final int TICKS_IN_DAY = TICKS_IN_HOUR * 24;

    public static int hoursToTicks(int hour) {
        hour = MathHelper.clamp(hour, 0, 24);
        return hour * TICKS_IN_HOUR;
    }

    public static TimePeriod fromHours(int start, int stop) {
        return new TimePeriod(hoursToTicks(start), hoursToTicks(stop));
    }

    public static int clampDay(int ticks) {
        return MathHelper.clamp(ticks, 0, TICKS_IN_DAY);
    }

    public static int getDayTime(World world) {
        return (int) (world.getWorldTime() % TICKS_IN_DAY);
    }
}
