package aurocosh.divinefavor.common.config.minions;

import net.minecraftforge.common.config.Config;

public class MinionHusk {
    @Config.Name("Armor")
    public double armor = 2;
    @Config.Name("Armor toughness")
    public double armorToughness = 0;
    @Config.Name("Attack damage")
    public double attackDamage = 3;
    @Config.Name("Follow range")
    public double followRange = 64;
    @Config.Name("Knockback resistance")
    public double knockbackResistance = 0;
    @Config.Name("Max health")
    public double maxHealth = 20;
    @Config.Name("Movement speed")
    public double movementSpeed = 0.23d;
    @Config.Name("Swim speed")
    public int swimSpeed = 1;
}
