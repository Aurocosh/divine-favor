package aurocosh.divinefavor.common.config.entries.talismans.blade;

import net.minecraftforge.common.config.Config;

public class HungryBlade {
    @Config.Name("Favor cost")
    public int favorCost = 10;
    @Config.Name("Food stolen")
    public int foodStolen = 3;
    @Config.Name("Saturation stolen")
    public float saturationStolen = 5f;
}
