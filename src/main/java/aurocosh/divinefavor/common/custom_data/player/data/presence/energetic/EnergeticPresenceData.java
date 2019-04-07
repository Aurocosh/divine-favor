package aurocosh.divinefavor.common.custom_data.player.data.presence.energetic;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.lib.SimpleCounter;

// The default implementation of the capability. Holds all the logic.
public class EnergeticPresenceData {
    private final SimpleCounter counter;

    public EnergeticPresenceData() {
        counter = new SimpleCounter(ConfigPresence.energeticPresence.timeToRunOnWater);
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
