package aurocosh.divinefavor.common.config.talismans.spell;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class Starvation {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Duration")
    public int duration = UtilTick.minutesToTicks(2.5f);
    @Config.Name("Food per grass")
    public int foodPerGrass = 1;
    @Config.Name("Saturation per grass")
    public float saturationPerGrass = 0.1f;
}
