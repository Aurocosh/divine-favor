package aurocosh.divinefavor.common.config.talismans.spell;

import net.minecraftforge.common.config.Config;

public class HeatWave {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Radius")
    public double radius = 10;
    @Config.Name("Damage")
    public float damage = 1;
    @Config.Name("Enemy burn time")
    public int enemyBurnTime = 3;
    @Config.Name("Chance to set enemy on fire")
    public int chanceToSetEnemyOnFire = 80;
    @Config.Name("Chance to set ground on fire")
    public int chanceToSetGroundOnFire = 20;
}
