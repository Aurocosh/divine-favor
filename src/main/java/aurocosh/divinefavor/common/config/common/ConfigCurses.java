package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/curses")
public class ConfigCurses {
    @Config.Name("Curse resistance per curse")
    public static int curseResistancePerCurse = 50;
    @Config.Name("Base curse resistance")
    public static int baseCurseResistance = 5;
    @Config.Name("Player curse resistance")
    public static int playerCurseResistance = 5;
    @Config.Name("Mob resistances")
    public static Map<String, Integer> mobResistances = new HashMap<String, Integer>() {
        {
            put("minecraft:spider", 5);
            put("minecraft:skeleton", 15);
            put("minecraft:stray", 15);
            put("minecraft:zombie", 15);
            put("minecraft:husk", 15);
        }
    };
}