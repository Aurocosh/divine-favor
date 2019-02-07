package aurocosh.divinefavor.common.custom_data.player.data.presence.energetic;

import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.util.UtilTick;

// The default implementation of the capability. Holds all the logic.
public class EnergeticPresenceData {
    private static final int TIME_TO_RUN_ON_WATER = UtilTick.minutesToTicks(0.3f);
    private final SimpleCounter counter;

    public EnergeticPresenceData() {
        counter = new SimpleCounter(TIME_TO_RUN_ON_WATER);
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
