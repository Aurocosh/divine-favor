package aurocosh.divinefavor.common.config.entries.arrow_talismans;

import net.minecraftforge.common.config.Config;

public class ClimbingArrow {
    @Config.Name("Favor cost")
    public int favorCost;
    @Config.Name("Damage")
    public float damage;
    @Config.Name("Climbing distance")
    public float climbingDistance;
    @Config.Name("Climbing speed")
    public float climbingSpeed;
    @Config.Name("Despawn delay")
    public int despawnDelay;

    public ClimbingArrow(int favorCost, float damage, float climbingDistance, float climbingSpeed, int despawnDelay) {
        this.favorCost = favorCost;
        this.damage = damage;
        this.climbingDistance = climbingDistance;
        this.climbingSpeed = climbingSpeed;
        this.despawnDelay = despawnDelay;
    }
}
