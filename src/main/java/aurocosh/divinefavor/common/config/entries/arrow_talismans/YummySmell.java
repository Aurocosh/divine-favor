package aurocosh.divinefavor.common.config.entries.arrow_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class YummySmell {
    @Config.Name("Favor cost")
    public int favorCost = 140;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.secondsToTicks(30);
    @Config.Name("Effect rate")
    public int effectRate = UtilTick.INSTANCE.secondsToTicks(10);
    @Config.Name("Cure count")
    public int cureCount = 7;
    @Config.Name("Radius")
    public float radius = 25;
}
