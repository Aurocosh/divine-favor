package aurocosh.divinefavor.common.custom_data.living.petrification;

public interface IPetrificationHandler {
    void resetCureTimer();
    boolean cureTick();
    boolean damageTick();

    int getCureTicks();
    void setCureTicks(int ticks);
}
