package aurocosh.divinefavor.common.config.entries.talismans.blade;

import net.minecraftforge.common.config.Config;

public class LuckyStrike {
    @Config.Name("Favor cost")
    public int favorCost = 10;
    @Config.Name("Extra looting per hit")
    public int extraLootingPerHit = 1;
    @Config.Name("Extra looting cap")
    public int extraLootingCap = 15;
}
