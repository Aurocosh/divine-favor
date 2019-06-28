package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.spell_talismans.*;
import aurocosh.divinefavor.common.config.entries.spell_talismans.generic.*;
import aurocosh.divinefavor.common.util.UtilTick;
import net.minecraftforge.common.config.Config;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/spell_talismans")
public class ConfigSpell {
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
    @Config.Name("Extreme buoyancy")
    public static ExtremeBuoyancy extremeBuoyancy = new ExtremeBuoyancy();
    @Config.Name("Fall negation")
    public static FallNegation fallNegation = new FallNegation();
    @Config.Name("Fins")
    public static Fins fins = new Fins();
    @Config.Name("Flood")
    public static ReplaceSphere flood = new ReplaceSphere();
    @Config.Name("Focused fury")
    public static FocusedFury focusedFury = new FocusedFury();
    @Config.Name("Follow")
    public static Follow follow = new Follow();
    @Config.Name("Frost wave")
    public static FrostWave frostWave = new FrostWave();
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
    public static ReplaceSphere hellisphere = new ReplaceSphere();
    @Config.Name("Ice bubble")
    public static ReplacmentBubble iceBubble = new ReplacmentBubble();
    @Config.Name("Ice surface")
    public static SearchAndFlood iceSurface = new SearchAndFlood(20,3,150);
    @Config.Name("Infernal touch")
    public static InfernalTouch infernalTouch = new InfernalTouch();
    @Config.Name("Instand dive")
    public static InstantDive instantDive = new InstantDive();
    @Config.Name("Ignition")
    public static Ignition ignition = new Ignition();
    @Config.Name("Invite gem")
    public static InviteGem inviteGem = new InviteGem();
    @Config.Name("Invite pebble")
    public static InvitePebble invitePebble = new InvitePebble();
    @Config.Name("Lake thawing")
    public static SearchAndFlood lakeThawing = new SearchAndFlood(20,3,150);
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
    @Config.Name("Obsidian bubble")
    public static ReplacmentBubble obsidianBubble = new ReplacmentBubble();
    @Config.Name("Obsidian road")
    public static ObsidianRoad obsidianRoad = new ObsidianRoad();
    @Config.Name("Pearl crumbs")
    public static PearlCrumbs pearlCrumbs = new PearlCrumbs();
    @Config.Name("Piercing inferno")
    public static PiercingInferno piercingInferno = new PiercingInferno();
    @Config.Name("Ping")
    public static Ping ping = new Ping();
    @Config.Name("Place torch")
    public static PlaceTorch placeTorch = new PlaceTorch();
    @Config.Name("Prismatic eyes")
    public static PrismaticEyes prismaticEyes = new PrismaticEyes();
    @Config.Name("Red pulse")
    public static RedPulse redPulse = new RedPulse(2, 4, 4, UtilTick.INSTANCE.secondsToTicks(3));
    @Config.Name("Red signal")
    public static RedPulse redSignal = new RedPulse(20, 12, 15, UtilTick.INSTANCE.secondsToTicks(30));
    @Config.Name("Remote chest")
    public static RemoteChest remoteChest = new RemoteChest();
    @Config.Name("Searing pulse")
    public static SearingPulse searingPulse = new SearingPulse();
    @Config.Name("Sense ore approximate")
    public static SenseConfig senseOreApproximate = new SenseConfig(10, 25, 0.4f,4f);
    @Config.Name("Sense ore vague")
    public static SenseConfig senseOreVague = new SenseConfig(30, 25, 0.2f,2f);
    @Config.Name("Sense ore precise")
    public static SenseConfig senseOrePrecise = new SenseConfig(120, 25, 0.2f,0.45f);
    @Config.Name("Sense ore close")
    public static SenseConfig senseOreClose = new SenseConfig(2, 7, 0.2f,1f);
    @Config.Name("Sense block approximate")
    public static SenseConfig senseBlockApproximate = new SenseConfig(40, 25, 0.4f,4f);
    @Config.Name("Sense block vague")
    public static SenseConfig senseBlockVague = new SenseConfig(120, 25, 0.2f,2f);
    @Config.Name("Sense block precise")
    public static SenseConfig senseBlockPrecise = new SenseConfig(420, 25, 0.2f,0.45f);
    @Config.Name("Sense block close")
    public static SenseConfig senseBlockClose = new SenseConfig(10, 7, 0.2f,1f);
    @Config.Name("Sense water")
    public static SenseConfig senseWater = new SenseConfig(20, 25, 0.2f,2f);
    @Config.Name("Sense passive entities")
    public static SenseConfig senseEntityPassive = new SenseConfig(20, 25, 0.2f,2f);
    @Config.Name("Sense hostile entities")
    public static SenseConfig senseEntityHostile = new SenseConfig(20, 25, 0.2f,2f);
    @Config.Name("Sense player entities")
    public static SenseConfig senseEntityPlayer = new SenseConfig(20, 25, 0.2f,2f);
    @Config.Name("Sense all entities")
    public static SenseConfig senseEntityAll = new SenseConfig(20, 25, 0.2f,2f);
    @Config.Name("Sense lava")
    public static SenseConfig senseLava = new SenseConfig(50, 25, 0.2f,2f);
    @Config.Name("Sense liquid")
    public static SenseConfig senseLiquid = new SenseConfig(30, 25, 0.2f,2f);
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

    // New configs
    @Config.Name("Copy cuboid")
    public static CopyCuboid copyCuboid = new CopyCuboid();
    @Config.Name("Copy blocks")
    public static CopyBlocks copyBlocks = new CopyBlocks();
    @Config.Name("Crystalyze memory")
    public static CrystalyzeMemory crystalyzeMemory = new CrystalyzeMemory();
    @Config.Name("Build template")
    public static BuildTemplate buildTemplate = new BuildTemplate();
    @Config.Name("Copy area")
    public static CopyArea copyArea = new CopyArea();
    @Config.Name("Summon blaze")
    public static SummonBlaze summonBlaze = new SummonBlaze();
    @Config.Name("Summon cave spider")
    public static SummonCaveSpider summonCaveSpider = new SummonCaveSpider();
    @Config.Name("Summon spider")
    public static SummonSpider summonSpider = new SummonSpider();
    @Config.Name("Rotten might")
    public static RottenMight rottenMight = new RottenMight();
    @Config.Name("Push side")
    public static PushSide pushSide = new PushSide();
    @Config.Name("Pull side")
    public static PullSide pullSide = new PullSide();
    @Config.Name("Replace cuboid")
    public static ReplaceCuboid replaceCuboid = new ReplaceCuboid();
    @Config.Name("Replace side")
    public static ReplaceSide replaceSide = new ReplaceSide();
    @Config.Name("Replace blocks")
    public static ReplaceBlocks replaceBlocks = new ReplaceBlocks();
    @Config.Name("Build extrusion")
    public static BuildExtrusion buildExtrusion = new BuildExtrusion();
    @Config.Name("Build horizontal line")
    public static BuildHorizontalLine buildHorizontalLine = new BuildHorizontalLine();
    @Config.Name("Build from surface")
    public static BuildFromSurface buildFromSurface = new BuildFromSurface();
    @Config.Name("Build sphere")
    public static BuildSphere buildSphere = new BuildSphere();
    @Config.Name("Build hollow sphere")
    public static BuildHollowSphere buildHollowSphere = new BuildHollowSphere();
    @Config.Name("Build square wall")
    public static BuildSquareWall buildSquareWall = new BuildSquareWall();
    @Config.Name("Build square floor")
    public static BuildSquareFloor buildSquareFloor = new BuildSquareFloor();
    @Config.Name("Build floor")
    public static BuildFloor buildFloor = new BuildFloor();
    @Config.Name("Build wall")
    public static BuildWall buildWall = new BuildWall();
    @Config.Name("Replace surface")
    public static ReplaceSurface replaceSurface = new ReplaceSurface();
    @Config.Name("Build column")
    public static BuildColumn buildColumn = new BuildColumn();
    @Config.Name("Bind")
    public static BindIceArrows bindIceArrows = new BindIceArrows();
}