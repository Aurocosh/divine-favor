package aurocosh.divinefavor.common.config.entries.talismans.spell;

import net.minecraftforge.common.config.Config;

public class WinterBreath {
    @Config.Name("Favor cost")
    public int favorCost = 60;
    @Config.Name("Radius")
    public int radius = 10;
    @Config.Name("Cone size")
    public double coneTolerance = 0.8f;
    @Config.Name("Damage")
    public int damage = 1;
    @Config.Name("Knockback")
    public int knockback = 2;
    @Config.Name("Particle count")
    public int particleCount = 150;
}
