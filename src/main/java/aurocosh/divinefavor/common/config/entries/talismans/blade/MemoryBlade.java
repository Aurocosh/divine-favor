package aurocosh.divinefavor.common.config.entries.talismans.blade;

import net.minecraftforge.common.config.Config;

public class MemoryBlade {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Damage per hit")
    public float damagePerHit = 1;
    @Config.Name("Damage cap")
    public float damageCap = 20;
}
