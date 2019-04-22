package aurocosh.divinefavor.common.config.entries.auras;

import net.minecraftforge.common.config.Config;

public class DistortedAura {
    @Config.Name("Starting chance")
    public float startingChance = 0;
    @Config.Name("Chance increase")
    public float chanceIncrease = 0.1f;
    @Config.Name("Enderman radius")
    public int endermanRadius = 1;
}
