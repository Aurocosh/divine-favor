package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.FinsTalisman;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "_talismans")
public class ConfigTalismans {
    @Config.Name("Fins")
    public static FinsTalisman fins = new FinsTalisman(5,5);
}