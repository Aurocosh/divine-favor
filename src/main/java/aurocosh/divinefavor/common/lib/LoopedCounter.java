package aurocosh.divinefavor.common.lib;

import aurocosh.divinefavor.common.util.UtilMath;

public class LoopedCounter {
    private int tickRate;
    private int currentTicks;

    public LoopedCounter() {
        tickRate = 1;
        currentTicks = 0;
    }

    public LoopedCounter(int tickRate) {
        this.tickRate = tickRate;
        currentTicks = 0;
    }

    public int getTickRate() {
        return tickRate;
    }

    public void setTickRate(int tickRate) {
        this.tickRate = Math.max(1, Math.abs(tickRate));
    }

    public int getCount() {
        return currentTicks;
    }

    public void setCount(int ticks) {
        currentTicks = UtilMath.clamp(ticks, 0, tickRate);
    }

    public void reset() {
        currentTicks = 0;
    }

    public boolean tick() {
        if (currentTicks < tickRate) {
            currentTicks++;
            return false;
        }
        else {
            currentTicks = 0;
            return true;
        }
    }

    public boolean isFinished() {
        return currentTicks >= tickRate;
    }
}
