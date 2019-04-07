package aurocosh.divinefavor.common.config.talismans.spell;

import net.minecraftforge.common.config.Config;

public class MistBlade {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Extra damage")
    public float extraDamage = 8;
    @Config.Name("Extra ranged damage")
    public float extraRangedDamage = 8;
    @Config.Name("Fog start")
    public int fogStart = 4;
    @Config.Name("Fog end")
    public int fogEnd = 5;

}
