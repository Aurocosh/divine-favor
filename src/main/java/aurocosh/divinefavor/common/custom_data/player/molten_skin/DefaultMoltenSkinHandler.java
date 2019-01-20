package aurocosh.divinefavor.common.custom_data.player.molten_skin;

import aurocosh.divinefavor.common.lib.LimitedTimer;
import aurocosh.divinefavor.common.util.UtilTick;

// The default implementation of the capability. Holds all the logic.
public class DefaultMoltenSkinHandler implements IMoltenSkinHandler {
    private static int MAX_TIME_NOT_IN_LAVA = UtilTick.secondsToTicks(20f);
    private static int DAMAGE_DELAY = UtilTick.secondsToTicks(2.5f);
    private LimitedTimer timer;

    public DefaultMoltenSkinHandler() {
        timer = new LimitedTimer(MAX_TIME_NOT_IN_LAVA);
    }

    @Override
    public void setMaxTime() {
        setTime(MAX_TIME_NOT_IN_LAVA);
    }

    @Override
    public void setTime(int ticks) {
        timer.setTicks(ticks);
    }

    @Override
    public int getTicks() {
        return timer.getTicks();
    }

    @Override
    public void resetTime() {
        timer.reset();
    }

    @Override
    public void delay() {
        timer.setTicks(timer.getTicks() - DAMAGE_DELAY);
    }

    @Override
    public boolean tick() {
        return timer.tick();
    }
}
