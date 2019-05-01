package aurocosh.divinefavor.common.config.data;

import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraftforge.common.config.Config;

public class DoubleInterval {
    @Config.Name("Min")
    public double min;
    @Config.Name("Max")
    public double max;

    public DoubleInterval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double random() {
        return UtilRandom.INSTANCE.nextDouble(min, max);
    }
}
