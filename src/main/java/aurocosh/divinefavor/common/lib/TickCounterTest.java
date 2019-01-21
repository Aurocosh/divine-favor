package aurocosh.divinefavor.common.lib;

import aurocosh.divinefavor.common.util.UtilMath;

public class TickCounterTest {
    private int tickRate;
    private int currentTicks;

    public TickCounterTest() {
        tickRate = 1;
        currentTicks = 0;
    }

    public TickCounterTest(int tickRate) {
        this.tickRate = tickRate;
        currentTicks = 0;
    }

    public void setTickRate(int tickRate) {
        this.tickRate = Math.max(1, Math.abs(tickRate));
    }

    public void setCurrentTicks(int ticks) {
        currentTicks = UtilMath.clamp(ticks, 0, tickRate);
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
}
