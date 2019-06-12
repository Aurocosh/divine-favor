package aurocosh.divinefavor.common.config.entries.spell_talismans;

import net.minecraftforge.common.config.Config;

public class Combustion {
    @Config.Name("Favor cost")
    public int favorCost = 60;
    @Config.Name("Max stacks to smelt")
    public int maxStacksToSmelt = 20;
    @Config.Name("Smelting chance")
    public float smeltingChance = 0.75f;
    @Config.Name("Explosion power")
    public float explosionPower = 4;
    @Config.Name("Cause fire")
    public boolean causeFire = false;
    @Config.Name("Damage terrain")
    public boolean damageTerraing = true;
}
