package aurocosh.divinefavor.common.config.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class Gills {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Max time not in water")
    public int maxTimeNotInWater = UtilTick.secondsToTicks(10f);
    @Config.Name("Damage delay")
    public int damageDelay = UtilTick.secondsToTicks(2f);
}
