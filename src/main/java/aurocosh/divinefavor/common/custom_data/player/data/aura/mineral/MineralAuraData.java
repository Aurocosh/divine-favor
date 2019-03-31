package aurocosh.divinefavor.common.custom_data.player.data.aura.mineral;

import aurocosh.divinefavor.common.config.common.ConfigAura;
import aurocosh.divinefavor.common.lib.SimpleCounter;

public class MineralAuraData {
    private final SimpleCounter counter;

    public MineralAuraData() {
        counter = new SimpleCounter(ConfigAura.mineralAura.stoneToBreak);
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
