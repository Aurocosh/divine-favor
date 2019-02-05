package aurocosh.divinefavor.common.custom_data.player.data.arboreal_aura;

import aurocosh.divinefavor.common.lib.SimpleCounter;

// The default implementation of the capability. Holds all the logic.
public class ArborealAuraData {
    private static final int WOOD_TO_BREAK = 15;
    private final SimpleCounter counter;

    public ArborealAuraData() {
        counter = new SimpleCounter(WOOD_TO_BREAK);
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
