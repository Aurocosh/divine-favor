package aurocosh.divinefavor.common.config.talismans.arrow;

import net.minecraftforge.common.config.Config;

public class ReinforcedArrow {
    @Config.Name("Favor cost")
    public int favorCost;
    @Config.Name("Damage")
    public float damage;

    public ReinforcedArrow(int favorCost, float damage) {
        this.favorCost = favorCost;
        this.damage = damage;
    }
}
