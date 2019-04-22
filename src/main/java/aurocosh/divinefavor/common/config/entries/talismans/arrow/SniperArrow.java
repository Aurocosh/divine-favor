package aurocosh.divinefavor.common.config.entries.talismans.arrow;

import net.minecraftforge.common.config.Config;

public class SniperArrow {
    @Config.Name("Favor cost")
    public int favorCost = 80;
    @Config.Name("Damage per meter")
    public float damagePerMeter = 0.3f;
    @Config.Name("Minimal distance")
    public float minDistance = 15;
}
