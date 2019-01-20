package aurocosh.divinefavor.common.custom_data.player.gills;

import aurocosh.divinefavor.common.lib.LimitedTimer;
import aurocosh.divinefavor.common.util.UtilTick;

// The default implementation of the capability. Holds all the logic.
public class DefaultGillsHandler implements IGillsHandler {
    private static int MAX_TIME_NOT_IN_WATER = UtilTick.secondsToTicks(10f);
    private static int DAMAGE_DELAY = UtilTick.secondsToTicks(2.0f);
    private LimitedTimer timer;

    public DefaultGillsHandler() {
        timer = new LimitedTimer(MAX_TIME_NOT_IN_WATER);
    }

    @Override
    public void setMaxTime() {
        setTime(MAX_TIME_NOT_IN_WATER);
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
