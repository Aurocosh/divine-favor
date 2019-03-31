package aurocosh.divinefavor.common.custom_data.player.data.presence.predatory;

import aurocosh.divinefavor.common.config.common.ConfigPresence;
import aurocosh.divinefavor.common.lib.SimpleCounter;
import aurocosh.divinefavor.common.util.UtilRandom;

public class PredatoryPresenceData {
    private final SimpleCounter counter;

    public PredatoryPresenceData() {
        counter = new SimpleCounter(ConfigPresence.predatoryPresence.minMonstersToKill);
    }

    public boolean count() {
        return counter.count();
    }

    public void reset() {
        counter.setRequired(UtilRandom.nextInt(ConfigPresence.predatoryPresence.minMonstersToKill, ConfigPresence.predatoryPresence.maxMonstersToKill));
    }

    public int getCount() {
        return counter.getCount();
    }

    public void setCount(int count) {
        counter.setCount(count);
    }
}
