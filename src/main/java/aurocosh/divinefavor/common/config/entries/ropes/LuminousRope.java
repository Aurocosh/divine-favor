package aurocosh.divinefavor.common.config.entries.ropes;

import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

public class LuminousRope {
    @Config.Name("Light level")
    public int lightLevel = 15;
    @Config.Name("Light sources per node")
    public int ligtSourcesPerNode = 5;
    @Config.Name("Light sources radius")
    public int lighSourceRadius = 15;
    @Config.Name("Light sources deleay")
    public int lighSourceDelay = UtilTick.INSTANCE.secondsToTicks(5);
    @Config.Name("Light source duration")
    public int lightSourceDuration = UtilTick.INSTANCE.minutesToTicks(3);
    @Config.Name("Do light sources time out")
    public boolean doLightsTimeOut = false;
}
