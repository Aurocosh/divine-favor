package aurocosh.divinefavor.common.config.talismans.arrow;

import net.minecraftforge.common.config.Config;

public class VacuumArrow {
    @Config.Name("Favor cost")
    public int favorCost = 80;
    @Config.Name("Damage")
    public float damage = 2;
    @Config.Name("Radius")
    public int radius = 6;
    @Config.Name("Attraction power")
    public float attractionPower = -0.05f;
}
