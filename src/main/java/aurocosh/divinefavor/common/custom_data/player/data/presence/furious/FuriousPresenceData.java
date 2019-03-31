package aurocosh.divinefavor.common.custom_data.player.data.presence.furious;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.util.UtilRandom;

public class FuriousPresenceData {
    private final SimpleCounter counter;

    public FuriousPresenceData() {
        counter = new SimpleCounter(ConfigPresence.furiousPresence.minMonstersToKill);
    }

    public boolean count() {
        return counter.count();
    }

    public void reset() {
        counter.setRequired(UtilRandom.nextInt(ConfigPresence.furiousPresence.minMonstersToKill, ConfigPresence.furiousPresence.maxMonstersToKill));
    }

    public int getCount() {
        return counter.getCount();
    }

    public void setCount(int count) {
        counter.setCount(count);
    }
}
