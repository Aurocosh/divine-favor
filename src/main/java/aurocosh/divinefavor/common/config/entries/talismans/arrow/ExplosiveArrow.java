package aurocosh.divinefavor.common.config.entries.talismans.arrow;

import net.minecraftforge.common.config.Config;

public class ExplosiveArrow {
    @Config.Name("Favor cost")
    public int favorCost;
    @Config.Name("Damage")
    public float damage;
    @Config.Name("Explosion power")
    public int explosionPower;
    @Config.Name("Damage terrain")
    public boolean damageTerrain;
    @Config.Name("Cause fire")
    public boolean causeFire;

    public ExplosiveArrow(int favorCost, float damage, int explosionPower, boolean damageTerrain, boolean causeFire) {
        this.favorCost = favorCost;
        this.damage = damage;
        this.explosionPower = explosionPower;
        this.damageTerrain = damageTerrain;
        this.causeFire = causeFire;
    }
}
