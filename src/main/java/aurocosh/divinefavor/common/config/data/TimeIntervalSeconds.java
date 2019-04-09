package aurocosh.divinefavor.common.config.data;

import aurocosh.divinefavor.common.util.UtilRandom;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class TimeIntervalSeconds {
    @Config.Name("Min")
    @Config.Comment("Minimal time in seconds")
    public int min;
    @Config.Name("Max")
    @Config.Comment("Maximum time in seconds")
    public int max;

    public TimeIntervalSeconds(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int random() {
        return UtilTick.secondsToTicks(UtilRandom.nextInt(min, max));
    }
}
