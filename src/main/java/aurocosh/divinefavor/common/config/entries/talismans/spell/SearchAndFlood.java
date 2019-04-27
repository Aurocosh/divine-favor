package aurocosh.divinefavor.common.config.entries.talismans.spell;

import net.minecraftforge.common.config.Config;

public class SearchAndFlood {
    @Config.Name("Favor cost")
    public int favorCost;
    @Config.Name("Search radius")
    public int radius;
    @Config.Name("Flood limit")
    public int floodLimit;

    public SearchAndFlood(int favorCost, int radius, int floodLimit) {
        this.favorCost = favorCost;
        this.radius = radius;
        this.floodLimit = floodLimit;
    }
}
