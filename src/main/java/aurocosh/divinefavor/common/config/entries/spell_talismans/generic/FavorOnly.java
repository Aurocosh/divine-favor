package aurocosh.divinefavor.common.config.entries.spell_talismans.generic;

import net.minecraftforge.common.config.Config;

public class FavorOnly {
    @Config.Name("Favor cost")
    public int favorCost;

    public FavorOnly(int favorCost) {
        this.favorCost = favorCost;
    }
}
