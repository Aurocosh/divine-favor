package aurocosh.divinefavor.common.custom_data.player.data.aura.mineral;

import aurocosh.divinefavor.common.lib.SimpleCounter;

// The default implementation of the capability. Holds all the logic.
public class MineralAuraData {
    private static final int STONE_TO_BREAK = 30;
    private final SimpleCounter counter;

    public MineralAuraData() {
        counter = new SimpleCounter(STONE_TO_BREAK);
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
