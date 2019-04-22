package aurocosh.divinefavor.common.config.entries.talismans.arrow;

import net.minecraftforge.common.config.Config;

public class DestructiveArrow {
    @Config.Name("Favor cost")
    public int favorCost;
    @Config.Name("Damage")
    public float damage;
    @Config.Name("Max hardness")
    public int maxHardness;

    public DestructiveArrow(int favorCost, float damage, int maxHardness) {
        this.favorCost = favorCost;
        this.damage = damage;
        this.maxHardness = maxHardness;
    }
}
