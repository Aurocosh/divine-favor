package aurocosh.divinefavor.common.config.entries.talismans.blade;

import net.minecraftforge.common.config.Config;

public class Confusion {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Confusion chance")
    public float confusionChance = 0.3f;
    @Config.Name("Target selection radius")
    public int radius = 25;
}
