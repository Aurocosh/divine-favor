package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.tool_talismans.*;
import net.minecraftforge.common.config.Config;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/tool_talismans")
public class ConfigTool {
    @Config.Name("Break radius")
    public static BreakRadius breakRadius = new BreakRadius();

}