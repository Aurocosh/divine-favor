package aurocosh.divinefavor.common.config;

import net.minecraftforge.common.config.Config;

public class TimePeriodConfig {
    @Config.Name("Start")
    public int start;
    @Config.Name("Stop")
    public int stop;

    public TimePeriodConfig(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }
}
