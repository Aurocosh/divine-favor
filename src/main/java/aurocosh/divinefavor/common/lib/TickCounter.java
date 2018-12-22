package aurocosh.divinefavor.common.lib;

import aurocosh.divinefavor.common.util.UtilMath;

public class TickCounter {
    private int tickRate;
    private int currentTicks;

    public TickCounter() {
        tickRate = 1;
        currentTicks = 0;
    }

    public TickCounter(int tickRate) {
        this.tickRate = tickRate;
        currentTicks = 0;
    }

    public void setTickRate(int tickRate) {
        this.tickRate = Math.max(1, Math.abs(tickRate));
    }

    public void setCurrentTicks(int ticks) {
        currentTicks = UtilMath.clamp(ticks,0, ticks);
    }

    public boolean tick() {
        currentTicks++;
        if (currentTicks < tickRate)
            return false;
        currentTicks = 0;
        return true;
    }
}
