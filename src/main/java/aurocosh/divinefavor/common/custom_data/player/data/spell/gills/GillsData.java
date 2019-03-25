package aurocosh.divinefavor.common.custom_data.player.data.spell.gills;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.lib.LimitedTimer;

// The default implementation of the capability. Holds all the logic.
public class GillsData {
    private LimitedTimer timer;

    public GillsData() {
        timer = new LimitedTimer(ConfigSpells.gills.maxTimeNotInWater);
    }

    public void setMaxTime() {
        setTime(ConfigSpells.gills.maxTimeNotInWater);
    }

    public void setTime(int ticks) {
        timer.setTicks(ticks);
    }

    public int getTicks() {
        return timer.getTicks();
    }

    public void resetTime() {
        timer.reset();
    }

    public void delay() {
        timer.setTicks(timer.getTicks() - ConfigSpells.gills.damageDelay);
    }

    public boolean tick() {
        return timer.tick();
    }
}
