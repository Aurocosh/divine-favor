package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.talismans.blade.RainSword;
import net.minecraftforge.common.config.Config;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/blade_talismans")
public class ConfigBlade {
    @Config.Name("Rain sword")
    public static RainSword rainSword = new RainSword();
}