package aurocosh.divinefavor.common.config.entries.arrow_talismans;

import net.minecraftforge.common.config.Config;

public class LuckyArrow {
    @Config.Name("Favor cost")
    public int favorCost = 30;
    @Config.Name("Damage")
    public float damage = 2;
    @Config.Name("Extra looting per hit")
    public int extraLootingPerHit = 3;
    @Config.Name("Extra looting cap")
    public int extraLootingCap = 15;
}