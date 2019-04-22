package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.talismans.spell.*;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/spell_talismans")
public class ConfigSpells {
    @Config.Name("Armor of pacifist")
    public static ArmorOfPacifist armorOfPacifist = new ArmorOfPacifist();
    @Config.Name("Arrow deflection")
    public static ArrowDeflection arrowDeflection = new ArrowDeflection();
    @Config.Name("Arrow throw")
    public static ArrowThrow arrowThrow = new ArrowThrow();
    @Config.Name("Blade of grass")
    public static BladeOfGrass bladeOfGrass = new BladeOfGrass();
    @Config.Name("Blazing palm")
    public static BlazingPalm blazingPalm = new BlazingPalm();
    @Config.Name("Blink")
    public static Blink blink = new Blink();
    @Config.Name("Blood of grass")
    public static BloodOfGrass bloodOfGrass = new BloodOfGrass();
    @Config.Name("Bonemeal")
    public static Bonemeal bonemeal = new Bonemeal();
    @Config.Name("Butchering strike")
    public static ButcheringStrike butcheringStrike = new ButcheringStrike();
    @Config.Name("Clock")
    public static Clock clock = new Clock();
    @Config.Name("Combustion")
    public static Combustion combustion = new Combustion();
    @Config.Name("Consuming fury")
    public static ConsumingFury consumingFury = new ConsumingFury();
    @Config.Name("Crushing palm")
    public static CrushingPalm crushingPalm = new CrushingPalm();
    @Config.Name("Crystalline road")
    public static CrystallineRoad crystallineRoad = new CrystallineRoad();
    @Config.Name("Distant spark")
    public static DistantSpark distantSpark = new DistantSpark();
    @Config.Name("Earthen dive")
    public static EarthenDive earthenDive = new EarthenDive();
    @Config.Name("Empower axe")
    public static EmpowerAxe empowerAxe = new EmpowerAxe();
    @Config.Name("Empower pickaxe")
    public static EmpowerPickaxe empowerPickaxe = new EmpowerPickaxe();
    @Config.Name("Escape plan")
    public static EscapePlan escapePlan = new EscapePlan();
    @Config.Name("Ethereal light")
    public static EtherealLight etherealLight = new EtherealLight();
    @Config.Name("Ethereal flash")
    public static EtherealFlash etherealFlash = new EtherealFlash();
    @Config.Name("Evil eye")
    public static EvilEye evilEye = new EvilEye();
    @Config.Name("Fall negation")
    public static FallNegation fallNegation = new FallNegation();
    @Config.Name("Fell tree")
    public static FellTree fellTree = new FellTree();
    @Config.Name("Fins")
    public static Fins fins = new Fins();
    @Config.Name("Focused fury")
    public static FocusedFury focusedFury = new FocusedFury();
    @Config.Name("Follow")
    public static Follow follow = new Follow();
    @Config.Name("Gills")
    public static Gills gills = new Gills();
    @Config.Name("Green cycle")
    public static GreenCycle greenCycle = new GreenCycle();
    @Config.Name("Ground flow")
    public static GroundFlow groundFlow = new GroundFlow();
    @Config.Name("Grudge")
    public static Grudge grudge = new Grudge();
    @Config.Name("harvest")
    public static Harvest harvest = new Harvest();
    @Config.Name("Heat wave")
    public static HeatWave heatWave = new HeatWave();
    @Config.Name("Hellisphere")
    public static Hellisphere hellisphere = new Hellisphere();
    @Config.Name("Infernal touch")
    public static InfernalTouch infernalTouch = new InfernalTouch();
    @Config.Name("Ignition")
    public static Ignition ignition = new Ignition();
    @Config.Name("Invite gem")
    public static InviteGem inviteGem = new InviteGem();
    @Config.Name("Invite pebble")
    public static InvitePebble invitePebble = new InvitePebble();
    @Config.Name("miners focus")
    public static MinersFocus minersFocus = new MinersFocus();
    @Config.Name("Mist blade")
    public static MistBlade mistBlade = new MistBlade();
    @Config.Name("Molten skin")
    public static MoltenSkin moltenSkin = new MoltenSkin();
    @Config.Name("Nether surge")
    public static NetherSurge netherSurge = new NetherSurge();
    @Config.Name("Night eye")
    public static NightEye nightEye = new NightEye();
    @Config.Name("Obsidian road")
    public static ObsidianRoad obsidianRoad = new ObsidianRoad();
    @Config.Name("Overblink")
    public static Overblink overblink = new Overblink();
    @Config.Name("Overwarp")
    public static Overwarp overwarp = new Overwarp();
    @Config.Name("Pearl crumbs")
    public static PearlCrumbs pearlCrumbs = new PearlCrumbs();
    @Config.Name("Piercing inferno")
    public static PiercingInferno piercingInferno = new PiercingInferno();
    @Config.Name("Ping")
    public static Ping ping = new Ping();
    @Config.Name("Prismatic eyes")
    public static PrismaticEyes prismaticEyes = new PrismaticEyes();
    @Config.Name("Red pulse")
    public static RedPulse redPulse = new RedPulse(2, 4, 4, UtilTick.secondsToTicks(3));
    @Config.Name("Red signal")
    public static RedPulse redSignal = new RedPulse(20, 12, 15, UtilTick.secondsToTicks(30));
    @Config.Name("Remote chest")
    public static RemoteChest remoteChest = new RemoteChest();
    @Config.Name("Searing pulse")
    public static SearingPulse searingPulse = new SearingPulse();
    @Config.Name("Sense ore approximate")
    public static SenseBlock senseOreApproximate = new SenseBlock(10, 25, 0.4f,4f);
    @Config.Name("Sense ore vague")
    public static SenseBlock senseOreVague = new SenseBlock(30, 25, 0.2f,2f);
    @Config.Name("Sense ore precise")
    public static SenseBlock senseOrePrecise = new SenseBlock(120, 25, 0.2f,0.45f);
    @Config.Name("Sense ore close")
    public static SenseBlock senseOreClose = new SenseBlock(2, 7, 0.2f,1f);
    @Config.Name("Sense ore approximate")
    public static SenseBlock senseBlockApproximate = new SenseBlock(40, 25, 0.4f,4f);
    @Config.Name("Sense ore vague")
    public static SenseBlock senseBlockVague = new SenseBlock(120, 25, 0.2f,2f);
    @Config.Name("Sense ore precise")
    public static SenseBlock senseBlockPrecise = new SenseBlock(420, 25, 0.2f,0.45f);
    @Config.Name("Sense ore close")
    public static SenseBlock senseBlockClose = new SenseBlock(10, 7, 0.2f,1f);
    @Config.Name("Small fireball throw")
    public static SmallFireballThrow smallFireballThrow = new SmallFireballThrow();
    @Config.Name("Snowball throw")
    public static SnowballThrow snowballThrow = new SnowballThrow();
    @Config.Name("Spider might")
    public static SpiderMight spider_might = new SpiderMight();
    @Config.Name("Starvation")
    public static Starvation starvation = new Starvation();
    @Config.Name("stone fever")
    public static StoneballThrow stoneballThrow = new StoneballThrow();
    @Config.Name("stoneball throw")
    public static StoneFever stoneFever = new StoneFever();
    @Config.Name("Summon creeper")
    public static SummonCreeper summonCreeper = new SummonCreeper();
    @Config.Name("Summon husk")
    public static SummonHusk summonHusk = new SummonHusk();
    @Config.Name("Summon skeleton")
    public static SummonSkeleton summonSkeleton = new SummonSkeleton();
    @Config.Name("Summon stray")
    public static SummonStray summonStray = new SummonStray();
    @Config.Name("Summon zombie")
    public static SummonZombie summonZombie = new SummonZombie();
    @Config.Name("Surface blink")
    public static SurfaceBlink surfaceBlink = new SurfaceBlink();
    @Config.Name("Surface shift")
    public static SurfaceShift surfaceShift = new SurfaceShift();
    @Config.Name("Target")
    public static Target target = new Target();
    @Config.Name("Toadic jump")
    public static ToadicJump toadicJump = new ToadicJump();
    @Config.Name("Vitalize")
    public static Vitalize vitalize = new Vitalize();
    @Config.Name("Wall slip")
    public static WallSlip wallSlip = new WallSlip();
    @Config.Name("Warp")
    public static Warp warp = new Warp();
    @Config.Name("Warp gem")
    public static WarpGem warpGem = new WarpGem();
    @Config.Name("Warp pebble")
    public static WarpPebble warpPebble = new WarpPebble();
    @Config.Name("Wild sprint")
    public static WildSprint wildSprint = new WildSprint();
    @Config.Name("Wind step")
    public static WindStep windStep = new WindStep();
    @Config.Name("Winter breath")
    public static WinterBreath winterBreath = new WinterBreath();
    @Config.Name("wooden punch")
    public static WoodenPunch woodenPunch = new WoodenPunch();
}