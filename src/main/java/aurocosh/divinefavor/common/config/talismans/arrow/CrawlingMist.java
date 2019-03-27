package aurocosh.divinefavor.common.config.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class CrawlingMist {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(3);
    @Config.Name("Fog start")
    public int fogStart = 20;
    @Config.Name("Fog end")
    public int fogEnd = 30;
    @Config.Name("Cure rate")
    public int cureRate = UtilTick.secondsToTicks(10);
    @Config.Name("Cure distance")
    public int cureDistance = 20;
}
