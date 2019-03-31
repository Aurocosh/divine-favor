package aurocosh.divinefavor.common.custom_data.player.data.aura.frosty;

import aurocosh.divinefavor.common.config.common.ConfigAura;
import aurocosh.divinefavor.common.lib.SimpleCounter;

// The default implementation of the capability. Holds all the logic.
public class FrostyAuraData {
    private final SimpleCounter counter;

    public FrostyAuraData() {
        counter = new SimpleCounter(ConfigAura.frostyAura.timeInSnowBiome);
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
