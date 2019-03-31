package aurocosh.divinefavor.common.custom_data.player.data.aura.arboreal;

import aurocosh.divinefavor.common.config.common.ConfigAura;
import aurocosh.divinefavor.common.lib.SimpleCounter;

// The default implementation of the capability. Holds all the logic.
public class ArborealAuraData {
    private final SimpleCounter counter;

    public ArborealAuraData() {
        counter = new SimpleCounter(ConfigAura.arborealAura.woodToBreak);
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
