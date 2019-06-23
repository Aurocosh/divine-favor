package aurocosh.divinefavor.common.config.entries.minions;

import net.minecraftforge.common.config.Config;

public class MinionBlaze {
    @Config.Name("Armor")
    public double armor = 0;
    @Config.Name("Armor toughness")
    public double armorToughness = 0;
    @Config.Name("Attack damage")
    public double attackDamage = 6;
    @Config.Name("Follow range")
    public double followRange = 48;
    @Config.Name("Knockback resistance")
    public double knockbackResistance = 0;
    @Config.Name("Max health")
    public double maxHealth = 20;
    @Config.Name("Movement speed")
    public double movementSpeed = 0.2d;
    @Config.Name("Swim speed")
    public int swimSpeed = 1;
}
