package aurocosh.divinefavor.common.config.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class MineArrow {
    @Config.Name("Favor cost")
    public int favorCost = 80;
    @Config.Name("Damage")
    public float damage = 2;
    @Config.Name("Radius")
    public float radius = 0.5f;
    @Config.Name("Cause fire")
    public boolean causeFire = false;
    @Config.Name("Damage terrain")
    public boolean damageTerrain = false;
    @Config.Name("Explosion power")
    public float explosionPower = 2;
    @Config.Name("Despawn delay")
    public int despawnDelay = UtilTick.secondsToTicks(240);
}
