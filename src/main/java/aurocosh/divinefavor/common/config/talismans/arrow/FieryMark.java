package aurocosh.divinefavor.common.config.talismans.arrow;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class FieryMark {
    @Config.Name("Favor cost")
    public int favorCost = 5;
    @Config.Name("Duration")
    public int duration = UtilTick.secondsToTicks(10);
    @Config.Name("Explosion power")
    public int explosionPower = 4;
    @Config.Name("Cause fire")
    public boolean causeFire = false;
    @Config.Name("Damage terrain")
    public boolean damageTerrain =  true;
    @Config.Name("On fire seconds")
    public int onFireSeconds = 3;
    @Config.Name("Extra damage")
    public float extraDamage = 20;
}
