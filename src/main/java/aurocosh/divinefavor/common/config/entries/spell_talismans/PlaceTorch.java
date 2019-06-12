package aurocosh.divinefavor.common.config.entries.spell_talismans;

import net.minecraftforge.common.config.Config;

public class PlaceTorch {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Requires torches in inventory")
    public boolean requiresTorches = true;
}