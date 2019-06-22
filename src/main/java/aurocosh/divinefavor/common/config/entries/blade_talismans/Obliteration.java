package aurocosh.divinefavor.common.config.entries.blade_talismans;

import net.minecraftforge.common.config.Config;

public class Obliteration {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Deny experience")
    public boolean denyExperience = true;
    @Config.Name("Extra damage")
    public float damage = 30;
}
