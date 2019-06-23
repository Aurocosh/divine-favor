package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.curses.*;
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

    @Config.Name("Armor corrosion")
    public static ArmorCorrosion armorCorrosion = new ArmorCorrosion();
    @Config.Name("Crawling mist")
    public static CrawlingMist crawlingMist = new CrawlingMist();
    @Config.Name("Cripple")
    public static Cripple cripple = new Cripple();
    @Config.Name("Fiery mark")
    public static FieryMark fieryMark = new FieryMark();
    @Config.Name("Fill lungs")
    public static FillLungs fillLungs = new FillLungs();
    @Config.Name("Hollow leg")
    public static HollowLeg hollowLeg = new HollowLeg();
    @Config.Name("Limp leg")
    public static LimpLeg limpLeg = new LimpLeg();
    @Config.Name("Petrification")
    public static Petrification petrification = new Petrification();
    @Config.Name("Roots")
    public static Roots roots = new Roots();
    @Config.Name("Skyfall")
    public static Skyfall skyfall = new Skyfall();
    @Config.Name("Suffocating fumes")
    public static SuffocatingFumes suffocatingFumes = new SuffocatingFumes();
    @Config.Name("Wind leash")
    public static WindLeash windLeash = new WindLeash();
    @Config.Name("Yummy smell")
    public static YummySmell yummySmell = new YummySmell();
}