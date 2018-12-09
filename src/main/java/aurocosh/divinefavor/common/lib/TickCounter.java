package aurocosh.divinefavor.common.lib;

public class TickCounter {
    private int tickRate;
    private int currentTicks = 0;

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

    public boolean tick() {
        currentTicks++;
        if (currentTicks < tickRate)
            return false;
        currentTicks = 0;
        return true;
    }
}
