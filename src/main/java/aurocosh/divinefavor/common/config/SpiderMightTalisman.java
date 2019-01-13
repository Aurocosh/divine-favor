package aurocosh.divinefavor.common.config;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class SpiderMightTalisman {
    @Config.Name("Uses")
    public int uses;
    @Config.Name("Duration")
    public int duration;

    public SpiderMightTalisman(int uses, float durationMinutes) {
        this.uses = uses;
        this.duration = UtilTick.minutesToTicks(durationMinutes);
    }
}
