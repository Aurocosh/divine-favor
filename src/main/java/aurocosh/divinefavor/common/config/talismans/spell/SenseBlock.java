package aurocosh.divinefavor.common.config.talismans.spell;

import net.minecraftforge.common.config.Config;

public class SenseBlock {
    @Config.Name("Favor cost")
    public int favorCost;
    @Config.Name("Radius")
    public int radius;
    @Config.Name("Min shift")
    public float minShift;
    @Config.Name("Max shift")
    public float maxShift;

    public SenseBlock(int favorCost, int radius, float minShift, float maxShift) {
        this.favorCost = favorCost;
        this.radius = radius;
        this.minShift = minShift;
        this.maxShift = maxShift;
    }
}
