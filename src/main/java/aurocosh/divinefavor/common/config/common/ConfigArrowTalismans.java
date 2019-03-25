package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.talismans.spell.Fins;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "_arrow_talismans")
public class ConfigArrowTalismans {
    @Config.Name("Fins")
    public static Fins fins = new Fins();
}