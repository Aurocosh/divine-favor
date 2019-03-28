package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.talismans.arrow.*;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/arrow_talismans")
public class ConfigArrow {
    @Config.Name("Anti gravity arrow")
    public static AntiGravityArrow antiGravityArrow = new AntiGravityArrow();
    @Config.Name("Armor corrosion")
    public static ArmorCorrosion armorCorrosion = new ArmorCorrosion();
    @Config.Name("Blink arrow")
    public static BlinkArrow blinkArrow = new BlinkArrow();
    @Config.Name("Crawling mist")
    public static CrawlingMist crawlingMist = new CrawlingMist();
    @Config.Name("Cripple")
    public static Cripple cripple = new Cripple();
    @Config.Name("Disarm")
    public static Disarm disarm = new Disarm();
    @Config.Name("Fiery mark")
    public static FieryMark fieryMark = new FieryMark();
    @Config.Name("Fill lungs")
    public static FillLungs fillLungs = new FillLungs();
    @Config.Name("Hand swap")
    public static HandSwap handSwap = new HandSwap();
    @Config.Name("Hollow leg")
    public static HollowLeg hollowLeg = new HollowLeg();
    @Config.Name("Limp leg")
    public static LimpLeg limpLeg = new LimpLeg();
    @Config.Name("Nether swap")
    public static NetherSwap netherSwap = new NetherSwap();
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