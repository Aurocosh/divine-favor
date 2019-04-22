package aurocosh.divinefavor.common.config.entries.ropes;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class ExplosiveRope {
    @Config.Name("Fuse delay")
    public int fuseDelay = UtilTick.secondsToTicks(4);
    @Config.Name("Explosion power")
    public float explosionPower = 2.0f;
}
