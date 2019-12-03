package aurocosh.divinefavor.common.config.entries.arrow_talismans;

import net.minecraftforge.common.config.Config;

public class PiercingArrow {
    @Config.Name("Favor cost")
    public int favorCost = 80;
    @Config.Name("Damage")
    public double damage = 2f;
    @Config.Name("Damage increase per hit")
    public double damageIncreasePerHit = 2;
    @Config.Name("Max hits")
    public int maxHits = 6;
}
