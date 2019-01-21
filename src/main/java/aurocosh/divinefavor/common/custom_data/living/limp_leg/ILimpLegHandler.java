package aurocosh.divinefavor.common.custom_data.living.limp_leg;

public interface ILimpLegHandler {
    void resetCureTimer();
    boolean cureTick();

    int getCureTicks();
    void setCureTicks(int ticks);
}
