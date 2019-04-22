package aurocosh.divinefavor.common.config.entries.talismans.arrow;

import net.minecraftforge.common.config.Config;

public class PiercingArrow {
    @Config.Name("Favor cost")
    public int favorCost = 40;
    @Config.Name("Damage")
    public double damage = 2f;
    @Config.Name("Damage increase per hit")
    public double damageIncreasePerHit = 1;
    @Config.Name("Max hits")
    public int maxHits = 4;
}
