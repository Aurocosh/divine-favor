package aurocosh.divinefavor.common.config.entries.talismans.spell;

import net.minecraftforge.common.config.Config;

public class HeatWave {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Radius")
    public double radius = 10;
    @Config.Name("Damage")
    public float damage = 1;
    @Config.Name("Enemy burn time")
    public int enemyBurnTime = 3;
    @Config.Name("Chance to set enemy on fire")
    public float chanceToSetEnemyOnFire = 0.8f;
    @Config.Name("Chance to set ground on fire")
    public float chanceToSetGroundOnFire = 0.2f;
    @Config.Name("Particle count")
    public int particleCount = 150;
}
