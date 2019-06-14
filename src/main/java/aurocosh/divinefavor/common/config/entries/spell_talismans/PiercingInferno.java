package aurocosh.divinefavor.common.config.entries.spell_talismans;

import net.minecraftforge.common.config.Config;

public class PiercingInferno {
    @Config.Name("Favor per block")
    public int favorCost = 1;
    @Config.Name("Max breaking speed")
    public int maxPierceDepth = 32;
    @Config.Name("Max tunnel surface")
    public int maxTunnelSurface = 24;
    @Config.Name("chanceToIgnite")
    public int chanceToIgnite = 20;
}
