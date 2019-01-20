package aurocosh.divinefavor.common.custom_data.player.molten_skin;

public interface IMoltenSkinHandler {
    void setMaxTime();
    void setTime(int ticks);
    int getTicks();

    void resetTime();
    void delay();
    boolean tick();
}
