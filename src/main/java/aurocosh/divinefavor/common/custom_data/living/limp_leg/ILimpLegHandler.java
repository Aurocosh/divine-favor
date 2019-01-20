package aurocosh.divinefavor.common.custom_data.living.limp_leg;

public interface ILimpLegHandler {
    void reset();
    boolean sneakingTick();

    int getCurrentTicks();
    void setCurrentTicks(int ticks);
}
