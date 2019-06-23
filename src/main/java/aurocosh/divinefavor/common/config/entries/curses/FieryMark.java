package aurocosh.divinefavor.common.config.entries.curses;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class FieryMark {
    @Config.Name("Duration")
    public int duration = UtilTick.INSTANCE.secondsToTicks(10);
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
