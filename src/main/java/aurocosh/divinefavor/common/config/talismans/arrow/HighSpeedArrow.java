package aurocosh.divinefavor.common.config.talismans.arrow;

import net.minecraftforge.common.config.Config;

public class HighSpeedArrow {
    @Config.Name("Favor cost")
    public int favorCost;
    @Config.Name("Damage")
    public float damage;
    @Config.Name("Extra velocity")
    public float extraVelocity;

    public HighSpeedArrow(int favorCost, float damage, float extraVelocity) {
        this.favorCost = favorCost;
        this.damage = damage;
        this.extraVelocity = extraVelocity;
    }
}
