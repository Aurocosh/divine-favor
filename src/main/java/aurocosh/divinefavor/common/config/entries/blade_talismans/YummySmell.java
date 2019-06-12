package aurocosh.divinefavor.common.config.entries.blade_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class YummySmell {
    @Config.Name("Favor cost")
    public int favorCost = 140;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.secondsToTicks(30);
}
