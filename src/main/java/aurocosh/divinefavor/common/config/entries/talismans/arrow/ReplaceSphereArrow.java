package aurocosh.divinefavor.common.config.entries.talismans.arrow;

import net.minecraftforge.common.config.Config;

public class ReplaceSphereArrow {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Damage")
    public float damage = 2;
    @Config.Name("Radius")
    public int radius = 5;

    public ReplaceSphereArrow(int favorCost, float damage, int radius) {
        this.favorCost = favorCost;
        this.damage = damage;
        this.radius = radius;
    }
}
