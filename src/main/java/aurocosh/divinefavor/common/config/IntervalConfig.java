package aurocosh.divinefavor.common.config;

import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraftforge.common.config.Config;

public class IntervalConfig {
    @Config.Name("Min")
    public int min;
    @Config.Name("Max")
    public int max;

    public IntervalConfig(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getRandom() {
        return UtilRandom.nextInt(min, max);
    }
}
