package aurocosh.divinefavor.common.lib;

import aurocosh.divinefavor.common.util.UtilMath;

public class LimitedTimer {
    private int limit;
    private int ticks;

    public LimitedTimer() {
        limit = 1;
        ticks = 0;
    }

    public LimitedTimer(int limit) {
        this.limit = limit;
        ticks = 0;
    }

    public void reset() {
        this.ticks = 0;
    }

    public void setLimit(int limit) {
        this.limit = Math.max(1, Math.abs(limit));
    }

    public void setTicks(int ticks) {
        this.ticks = UtilMath.clamp(ticks,0, limit);
    }

    public int getTicks() {
        return ticks;
    }

    public boolean tick() {
        if (ticks >= limit)
            return true;
        ticks++;
        return false;
    }
}
