package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.talismans.arrow.*;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/arrow_talismans")
public class ConfigArrow {
    @Config.Name("Anti gravity arrow")
    public static AntiGravityArrow antiGravityArrow = new AntiGravityArrow();
    @Config.Name("Armor corrosion")
    public static ArmorCorrosion armorCorrosion = new ArmorCorrosion();
    @Config.Name("Blast arrow")
    public static ExplosiveArrow blastArrow = new ExplosiveArrow(30, 2, 2, false, false);
    @Config.Name("Blink arrow")
    public static BlinkArrow blinkArrow = new BlinkArrow();
    @Config.Name("Climbing arrow")
    public static ClimbingArrow climbingArrow = new ClimbingArrow(10, 2, 2.5f, 0.3f, UtilTick.secondsToTicks(60));
    @Config.Name("Crawling mist")
    public static CrawlingMist crawlingMist = new CrawlingMist();
    @Config.Name("Cripple")
    public static Cripple cripple = new Cripple();
    @Config.Name("Destructive arrow I")
    public static DestructiveArrow destructiveArrow1 = new DestructiveArrow(10, 2, 1);
    @Config.Name("Destructive arrow II")
    public static DestructiveArrow destructiveArrow2 = new DestructiveArrow(100, 2, 3);
    @Config.Name("Destructive arrow III")
    public static DestructiveArrow destructiveArrow3 = new DestructiveArrow(300, 2, 50);
    @Config.Name("Disarm")
    public static Disarm disarm = new Disarm();
    @Config.Name("Explosive arrow")
    public static ExplosiveArrow explosiveArrow = new ExplosiveArrow(120, 2, 5, true, false);
    @Config.Name("Fiery mark")
    public static FieryMark fieryMark = new FieryMark();
    @Config.Name("Fill lungs")
    public static FillLungs fillLungs = new FillLungs();
    @Config.Name("Hand swap")
    public static HandSwap handSwap = new HandSwap();
    @Config.Name("High speed arrow")
    public static HighSpeedArrow highSpeedArrow = new HighSpeedArrow(40,2,2);
    @Config.Name("Hyper speed arrow")
    public static HighSpeedArrow hyperSpeedArrow = new HighSpeedArrow(400,2,6);
    @Config.Name("Hollow leg")
    public static HollowLeg hollowLeg = new HollowLeg();
    @Config.Name("Hover bubble arrow")
    public static ClimbingArrow hoverBubbleArrow = new ClimbingArrow(150, 2, 12, 0.3f, UtilTick.secondsToTicks(60));
    @Config.Name("Force arrow")
    public static ForceArrow forceArrow = new ForceArrow(120, 0.5f, 6);
    @Config.Name("Impulse arrow")
    public static ForceArrow impulseArrow = new ForceArrow(30, 0.5f, 2);
    @Config.Name("Limp leg")
    public static LimpLeg limpLeg = new LimpLeg();
    @Config.Name("Nether swap")
    public static NetherSwap netherSwap = new NetherSwap();
    @Config.Name("Nuke arrow")
    public static ExplosiveArrow nukeArrow = new ExplosiveArrow(400, 2, 12, true, true);
    @Config.Name("Petrification")
    public static Petrification petrification = new Petrification();
    @Config.Name("Reinforced arrow I")
    public static ReinforcedArrow reinforcedArrow1 = new ReinforcedArrow(10, 4);
    @Config.Name("Reinforced arrow II")
    public static ReinforcedArrow reinforcedArrow2 = new ReinforcedArrow(100, 8);
    @Config.Name("Reinforced arrow III")
    public static ReinforcedArrow reinforcedArrow3 = new ReinforcedArrow(400, 16);
    @Config.Name("Roots")
    public static Roots roots = new Roots();
    @Config.Name("Skyfall")
    public static Skyfall skyfall = new Skyfall();
    @Config.Name("Sniper arrow")
    public static SniperArrow sniperArrow = new SniperArrow();
    @Config.Name("Stasis arrow")
    public static StasisArrow stasisArrow = new StasisArrow();
    @Config.Name("Suffocating fumes")
    public static SuffocatingFumes suffocatingFumes = new SuffocatingFumes();
    @Config.Name("Tracer arrow")
    public static TracerArrow tracerArrow = new TracerArrow();
    @Config.Name("Vacuum arrow")
    public static VacuumArrow vacuumArrow = new VacuumArrow();
    @Config.Name("Wind leash")
    public static WindLeash windLeash = new WindLeash();
    @Config.Name("Yummy smell")
    public static YummySmell yummySmell = new YummySmell();
    @Config.Name("Zero g arrow")
    public static ZeroGArrow zeroGArrow = new ZeroGArrow();
}