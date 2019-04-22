package aurocosh.divinefavor.common.config.entries.talismans.spell;

import net.minecraftforge.common.config.Config;

public class SpiderMight {
    @Config.Name("Favor cost")
    public int favorCost = 20;
    @Config.Name("Extra fire damage")
    public float fireDamage = 4;
    @Config.Name("Extra bane damage")
    @Config.Comment("Extra damage per level of bane of arthropods enchantment")
    public float baneDamage = 4;
}
