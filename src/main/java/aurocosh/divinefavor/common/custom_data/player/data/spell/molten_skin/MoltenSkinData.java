package aurocosh.divinefavor.common.custom_data.player.data.spell.molten_skin;

import aurocosh.divinefavor.common.lib.LimitedTimer;
import aurocosh.divinefavor.common.util.UtilTick;

// The default implementation of the capability. Holds all the logic.
public class MoltenSkinData {
    private static int MAX_TIME_NOT_IN_LAVA = UtilTick.secondsToTicks(20f);
    private static int DAMAGE_DELAY = UtilTick.secondsToTicks(2.5f);
    private LimitedTimer timer;

    public MoltenSkinData() {
        timer = new LimitedTimer(MAX_TIME_NOT_IN_LAVA);
    }

    public void setMaxTime() {
        setTime(MAX_TIME_NOT_IN_LAVA);
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
        timer.setTicks(timer.getTicks() - DAMAGE_DELAY);
    }

    public boolean tick() {
        return timer.tick();
    }
}
