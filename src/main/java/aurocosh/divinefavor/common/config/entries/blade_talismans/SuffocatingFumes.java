package aurocosh.divinefavor.common.config.entries.blade_talismans;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class SuffocatingFumes {
    @Config.Name("Favor cost")
    public int favorCost = 100;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(2);
}
