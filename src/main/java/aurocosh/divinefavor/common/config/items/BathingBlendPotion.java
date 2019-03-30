package aurocosh.divinefavor.common.config.items;

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
        this.duration = UtilTick.secondsToTicks(duration);
        this.rate = UtilTick.secondsToTicks(rate);
        this.extraPotionDuration = UtilTick.secondsToTicks(extraPotionDuration);
    }
}
