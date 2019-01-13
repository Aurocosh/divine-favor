package aurocosh.divinefavor.common.player_data.gills;

public interface IGillsHandler {
    void setMaxTime();
    void setTime(int ticks);
    int getTicks();

    void resetTime();
    void delay();
    boolean tick();
}
