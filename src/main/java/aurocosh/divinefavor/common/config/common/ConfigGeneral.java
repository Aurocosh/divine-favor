package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.SpiritActivityPeriods;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID +"/main")
public class ConfigGeneral {
    @Config.Name("Spirit activity periods")
    public static SpiritActivityPeriods spiritActivityPeriods = new SpiritActivityPeriods();

}