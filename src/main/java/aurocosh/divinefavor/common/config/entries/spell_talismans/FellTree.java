package aurocosh.divinefavor.common.config.entries.spell_talismans;

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
