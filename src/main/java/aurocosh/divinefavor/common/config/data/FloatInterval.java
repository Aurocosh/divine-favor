package aurocosh.divinefavor.common.config.data;

import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraftforge.common.config.Config;

public class FloatInterval {
    @Config.Name("Min")
    public float min;
    @Config.Name("Max")
    public float max;

    public FloatInterval(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public float random() {
        return UtilRandom.nextFloat(min, max);
    }
}
