package aurocosh.divinefavor.common.config.entries.arrow_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class CrawlingMist {
    @Config.Name("Favor cost")
    public int favorCost = 80;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(3);
    @Config.Name("Fog start")
    public int fogStart = 20;
    @Config.Name("Fog end")
    public int fogEnd = 30;
    @Config.Name("Cure rate")
    public int cureRate = UtilTick.INSTANCE.secondsToTicks(10);
    @Config.Name("Cure distance")
    public int cureDistance = 20;
}
