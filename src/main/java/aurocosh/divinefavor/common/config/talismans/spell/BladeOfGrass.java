package aurocosh.divinefavor.common.config.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class BladeOfGrass {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Radius")
    public int radius = 10;
    @Config.Name("Damage")
    public float damage = 2;
    @Config.Name("Slowness time")
    public int slownessTime = UtilTick.secondsToTicks(15);
    @Config.Name("Slowness level")
    public int slownessLevel = 1;

}
