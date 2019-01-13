package aurocosh.divinefavor.common.config;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class FinsTalisman {
    @Config.Name("Uses")
    public int uses;
    @Config.Name("Duration")
    public int duration;

    public FinsTalisman(int uses, float durationMinutes) {
        this.uses = uses;
        this.duration = UtilTick.minutesToTicks(durationMinutes);
    }
}
