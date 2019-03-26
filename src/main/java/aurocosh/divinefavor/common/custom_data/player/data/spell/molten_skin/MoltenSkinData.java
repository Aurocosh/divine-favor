package aurocosh.divinefavor.common.custom_data.player.data.spell.molten_skin;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.lib.LimitedTimer;

// The default implementation of the capability. Holds all the logic.
public class MoltenSkinData {
    private LimitedTimer timer;

    public MoltenSkinData() {
        timer = new LimitedTimer(ConfigSpells.moltenSkin.maxTimeOutsideLava);
    }

    public void setMaxTime() {
        setTime(ConfigSpells.moltenSkin.maxTimeOutsideLava);
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
        timer.setTicks(timer.getTicks() - ConfigSpells.moltenSkin.damageDelay);
    }

    public boolean tick() {
        return timer.tick();
    }
}
