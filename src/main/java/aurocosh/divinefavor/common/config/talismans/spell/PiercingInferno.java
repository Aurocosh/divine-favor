package aurocosh.divinefavor.common.config.talismans.spell;

import net.minecraftforge.common.config.Config;

public class PiercingInferno {
    @Config.Name("Favor cost")
    public int favorCost = 80;
    @Config.Name("Blocks to break weak")
    public int blocksToBreakWeak = 30;
    @Config.Name("Blocks to break normal")
    public int blocksToBreakNormal = 150;
    @Config.Name("Max breaking speed")
    public int maxPierceDepth = 2;
    @Config.Name("chanceToIgnite")
    public int chanceToIgnite = 20;
}
