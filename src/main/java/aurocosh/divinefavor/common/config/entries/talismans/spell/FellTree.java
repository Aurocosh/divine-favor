package aurocosh.divinefavor.common.config.entries.talismans.spell;

import net.minecraftforge.common.config.Config;

public class FellTree {
    @Config.Name("Favor cost")
    public int favorCost = 60;
    @Config.Name("Min leaf count")
    public int minLeafCount = 10;
    @Config.Name("Max logs broken")
    public int maxLogsBroken = 500;
    @Config.Name("Breaking speed")
    public int breakingSpeed = 2;
}
