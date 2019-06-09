package aurocosh.divinefavor.common.config.entries.talismans.blade;

import net.minecraftforge.common.config.Config;

public class Gamble {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Damage")
    public float damage = 10;
    @Config.Name("Fail probability")
    public float failProbability = 0.05f;
    @Config.Name("Fail damage")
    public float failDamage = 32;
}
