package aurocosh.divinefavor.common.custom_data.player.data.presence.industrious;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.lib.SimpleCounter;

public class IndustriousPresenceData {
    private final SimpleCounter counter;

    public IndustriousPresenceData() {
        counter = new SimpleCounter(ConfigPresence.industriousPresence.oreToBreak);
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
