package aurocosh.divinefavor.common.config.entries.spell_talismans.generic;

import net.minecraftforge.common.config.Config;

public class SenseConfig {
    @Config.Name("Favor cost")
    public int favorCost;
    @Config.Name("Radius")
    public int radius;
    @Config.Name("Min shift")
    public float minShift;
    @Config.Name("Max shift")
    public float maxShift;

    public SenseConfig(int favorCost, int radius, float minShift, float maxShift) {
        this.favorCost = favorCost;
        this.radius = radius;
        this.minShift = minShift;
        this.maxShift = maxShift;
    }
}
