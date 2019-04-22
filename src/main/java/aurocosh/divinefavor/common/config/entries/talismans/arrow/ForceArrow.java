package aurocosh.divinefavor.common.config.entries.talismans.arrow;

import net.minecraftforge.common.config.Config;

public class ForceArrow {
    @Config.Name("Favor cost")
    public int favorCost = 60;
    @Config.Name("Damage")
    public float damage = 0.5f;
    @Config.Name("Velocity")
    public float velocity = 6;

    public ForceArrow(int favorCost, float damage, float velocity) {
        this.favorCost = favorCost;
        this.damage = damage;
        this.velocity = velocity;
    }
}

