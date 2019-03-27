package aurocosh.divinefavor.common.config.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class YummySmell {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Duration")
    public int duration = UtilTick.secondsToTicks(30);
    @Config.Name("Effect rate")
    public int effectRate = UtilTick.secondsToTicks(10);
    @Config.Name("Cure count")
    public int cureCount = 7;
    @Config.Name("Radius")
    public float radius = 25;
}
