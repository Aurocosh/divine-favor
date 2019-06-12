package aurocosh.divinefavor.common.config.entries.spell_talismans;

import net.minecraftforge.common.config.Config;

public class StoneballThrow {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Damage")
    public float damage = 0.5f;
    @Config.Name("Critical chance")
    public float criticalChance = 0.5f;
    @Config.Name("Critical damage")
    public float criticalDamage = 18;
    @Config.Name("Extra skeleton damage")
    public float extraSkeletonDamage = 4f;
}
