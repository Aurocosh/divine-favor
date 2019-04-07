package aurocosh.divinefavor.common.config.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class LimpLeg {
    @Config.Name("Favor cost")
    public int favorCost = 120;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(1);
}
