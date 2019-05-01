package aurocosh.divinefavor.common.config.entries.items;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class BathingBlendPotion {
    @Config.Name("Duration")
    public int duration;
    @Config.Name("Rate")
    public int rate;
    @Config.Name("Extra potion duration")
    public int extraPotionDuration;

    public BathingBlendPotion(int duration, int rate, int extraPotionDuration) {
        this.duration = UtilTick.INSTANCE.secondsToTicks(duration);
        this.rate = UtilTick.INSTANCE.secondsToTicks(rate);
        this.extraPotionDuration = UtilTick.INSTANCE.secondsToTicks(extraPotionDuration);
    }
}
