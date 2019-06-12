package aurocosh.divinefavor.common.config.entries.items;

import net.minecraftforge.common.config.Config;

public class IceArrow {
    @Config.Name("Damage")
    public float damage = 1.5f;
    @Config.Name("Extra damage to hellish mobs")
    public float damageHellishExtra = 4.5f;
    @Config.Name("Breaking probability")
    public float breakProbability = 0.65f;
}
