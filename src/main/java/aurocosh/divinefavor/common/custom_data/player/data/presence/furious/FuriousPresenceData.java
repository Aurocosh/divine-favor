package aurocosh.divinefavor.common.custom_data.player.data.presence.furious;

import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.util.UtilRandom;

// The default implementation of the capability. Holds all the logic.
public class FuriousPresenceData {
    private static final int MIN_MONSTERS_TO_KILL = 5;
    private static final int MAX_MONSTERS_TO_KILL = 10;
    private final SimpleCounter counter;

    public FuriousPresenceData() {
        counter = new SimpleCounter(MIN_MONSTERS_TO_KILL);
    }

    public boolean count() {
        return counter.count();
    }

    public void reset() {
        counter.setRequired(UtilRandom.nextInt(MIN_MONSTERS_TO_KILL, MAX_MONSTERS_TO_KILL));
    }

    public int getCount() {
        return counter.getCount();
    }

    public void setCount(int count) {
        counter.setCount(count);
    }
}
