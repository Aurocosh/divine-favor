package aurocosh.divinefavor.common.config.talismans.arrow;

import net.minecraftforge.common.config.Config;

public class RicochetArrow {
    @Config.Name("Favor cost")
    public int favorCost = 80;
    @Config.Name("Damage")
    public float damage = 2;
    @Config.Name("Radius")
    public int maxBounces = 5;
    @Config.Name("Min bounce speed")
    public float minBounceSpeed = 0.3f;
    @Config.Name("Bounce randomness")
    public float bounceRandomness = 0.3f;
    @Config.Name("Bounce speed decrease")
    public float bounceSpeedDecrease = 0.75f;
}
