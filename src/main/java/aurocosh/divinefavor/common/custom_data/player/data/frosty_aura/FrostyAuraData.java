package aurocosh.divinefavor.common.custom_data.player.data.frosty_aura;

import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.util.UtilTick;

// The default implementation of the capability. Holds all the logic.
public class FrostyAuraData {
    private static final int TIME_IN_SNOW_BIOME = UtilTick.minutesToTicks(0.6f);

    private final SimpleCounter counter;

    public FrostyAuraData() {
        counter = new SimpleCounter(TIME_IN_SNOW_BIOME);
    }

    public boolean count() {
        return counter.count();
    }

    public void reset() {
        counter.reset();
    }

    public int getCount() {
        return counter.getCount();
    }

    public void setCount(int count) {
        counter.setCount(count);
    }
}
