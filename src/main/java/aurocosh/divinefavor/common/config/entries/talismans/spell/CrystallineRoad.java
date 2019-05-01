package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class CrystallineRoad {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.minutesToTicks(5);
}
