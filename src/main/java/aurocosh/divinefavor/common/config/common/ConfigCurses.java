package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.lib.builders.map.Maps;
import net.minecraftforge.common.config.Config;

import java.util.Map;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/curses")
public class ConfigCurses {
    @Config.Name("Curse resistance per curse")
    public static float curseResistancePerCurse = 50;
    @Config.Name("Base curse resistance")
    public static float baseCurseResistance = 5;
    @Config.Name("Player curse resistance")
    public static float playerCurseResistance = 5;
    @Config.Name("Mob resistances")
    public static Map<String, Float> mobResistances = Maps.<String, Float>builder()
            .put("minecraft:spider", 5f)
            .put("minecraft:spider", 5f)
            .put("minecraft:skeleton", 15f)
            .put("minecraft:stray", 15f)
            .put("minecraft:zombie", 15f)
            .put("minecraft:husk", 15f)
            .build();
}