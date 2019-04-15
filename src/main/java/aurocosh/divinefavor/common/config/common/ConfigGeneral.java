package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/general")
public class ConfigGeneral {
    @Config.Name("Particle radius")
    public static int particleRadius = 30;
    @Config.Name("Max climbing radius")
    public static int maxClimbingRadius = 12;
}