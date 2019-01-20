package aurocosh.divinefavor.common.custom_data.player.gills;

public interface IGillsHandler {
    void setMaxTime();
    void setTime(int ticks);
    int getTicks();

    void resetTime();
    void delay();
    boolean tick();
}
