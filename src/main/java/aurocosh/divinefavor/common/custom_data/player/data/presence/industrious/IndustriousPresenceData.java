package aurocosh.divinefavor.common.custom_data.player.data.presence.industrious;

import aurocosh.divinefavor.common.lib.SimpleCounter;

// The default implementation of the capability. Holds all the logic.
public class IndustriousPresenceData {
    private static final int ORE_TO_BREAK = 10;
    private final SimpleCounter counter;

    public IndustriousPresenceData() {
        counter = new SimpleCounter(ORE_TO_BREAK);
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
