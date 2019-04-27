package aurocosh.divinefavor.common.config.entries.talismans.spell;

import net.minecraftforge.common.config.Config;

public class FrostWave {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Radius")
    public double radius = 10;
    @Config.Name("Damage")
    public float damage = 1;
    @Config.Name("Enemy burn time")
    public float knockbackPower = 1;
    @Config.Name("Particle count")
    public int particleCount = 150;
}
