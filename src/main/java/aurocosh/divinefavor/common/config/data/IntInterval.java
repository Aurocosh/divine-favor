package aurocosh.divinefavor.common.config.data;

import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraftforge.common.config.Config;

public class IntInterval {
    @Config.Name("Min")
    public int min;
    @Config.Name("Max")
    public int max;

    public IntInterval(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int random() {
        return UtilRandom.nextInt(min, max);
    }
}
