package aurocosh.divinefavor.common.config.entries.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class Gills {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Max time not in water")
    public int maxTimeNotInWater = UtilTick.INSTANCE.secondsToTicks(10f);
    @Config.Name("Damage delay")
    public int damageDelay = UtilTick.INSTANCE.secondsToTicks(2f);
}
