package aurocosh.divinefavor.common.config.talismans.spell;

import net.minecraftforge.common.config.Config;

public class StoneballThrow {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Damage")
    public float damage = 0.5f;
    @Config.Name("Critical chance")
    public float criticalChance = 5;
    @Config.Name("Critical damage")
    public float criticalDamage = 30;
}
