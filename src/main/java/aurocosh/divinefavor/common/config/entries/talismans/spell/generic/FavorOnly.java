package aurocosh.divinefavor.common.config.entries.talismans.spell.generic;

import net.minecraftforge.common.config.Config;

public class FavorOnly {
    @Config.Name("Favor cost")
    public int favorCost;

    public FavorOnly(int favorCost) {
        this.favorCost = favorCost;
    }
}
