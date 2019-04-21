package aurocosh.divinefavor.common.config.talismans.spell;

import net.minecraftforge.common.config.Config;

public class SearingPulse {
    @Config.Name("Favor cost")
    public int favorCost = 40;
    @Config.Name("Min neighbours to add")
    public int minNeighboursToAdd = 3;
    @Config.Name("Max neighbours to add")
    public int maxNeighboursToAdd = 4;
    @Config.Name("Min blocks to smelt")
    public int minBlocksToSmelt = 20;
    @Config.Name("Max blocks to smelt")
    public int maxBlocksToSmelt = 30;
    @Config.Name("Chance to create fire")
    public float chanceToCreateFire = 0.4f;
}
