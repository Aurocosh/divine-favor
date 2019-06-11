package aurocosh.divinefavor.common.config.entries.talismans.blade;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class Skyfall {
    @Config.Name("Favor cost")
    public int favorCost = 80;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.secondsToTicks(8);
}
