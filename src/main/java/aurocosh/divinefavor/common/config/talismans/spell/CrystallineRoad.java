package aurocosh.divinefavor.common.config.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class CrystallineRoad {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(5);
}
