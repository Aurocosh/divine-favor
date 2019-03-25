package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.talismans.spell.*;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "_spell_talismans")
public class ConfigSpells {
    @Config.Name("Arrow throw")
    public static ArrowThrow arrowThrow = new ArrowThrow();
    @Config.Name("Armor of pacifist")
    public static ArmorOfPacifist armorOfPacifist = new ArmorOfPacifist();
    @Config.Name("Crystalline road")
    public static CrystallineRoad crystallineRoad = new CrystallineRoad();
    @Config.Name("Fins")
    public static Fins fins = new Fins();
    @Config.Name("Gills")
    public static Gills gills = new Gills();
    @Config.Name("Obsidian road")
    public static ObsidianRoad obsidianRoad = new ObsidianRoad();
    @Config.Name("Snowball throw")
    public static SnowballThrow snowballThrow = new SnowballThrow();
    @Config.Name("Vitalize")
    public static Vitalize vitalize = new Vitalize();
    @Config.Name("Winter breath")
    public static WinterBreath winterBreath = new WinterBreath();
    @Config.Name("Blink")
    public static Blink blink = new Blink();
    @Config.Name("Earthen dive")
    public static EarthenDive earthenDive = new EarthenDive();
    @Config.Name("Escape plan")
    public static EscapePlan escapePlan = new EscapePlan();
    @Config.Name("Invite gem")
    public static InviteGem inviteGem = new InviteGem();
    @Config.Name("Invite pebble")
    public static InvitePebble invitePebble = new InvitePebble();
    @Config.Name("Overblink")
    public static Overblink overblink = new Overblink();
    @Config.Name("Overwarp")
    public static Overwarp overwarp = new Overwarp();
    @Config.Name("Pearl crumbs")
    public static PearlCrumbs pearlCrumbs = new PearlCrumbs();
    @Config.Name("Remote chest")
    public static RemoteChest remoteChest = new RemoteChest();
    @Config.Name("Surface blink")
    public static SurfaceBlink surfaceBlink = new SurfaceBlink();
    @Config.Name("Surface shift")
    public static SurfaceShift surfaceShift = new SurfaceShift();
    @Config.Name("Wall slip")
    public static WallSlip wallSlip = new WallSlip();
    @Config.Name("Warp")
    public static Warp warp = new Warp();
    @Config.Name("Warp gem")
    public static WarpGem warpGem = new WarpGem();
    @Config.Name("Warp pebble")
    public static WarpPebble warpPebble = new WarpPebble();
    @Config.Name("Follow")
    public static Follow follow = new Follow();
    @Config.Name("Night eye")
    public static NightEye nightEye = new NightEye();
    @Config.Name("Spider might")
    public static SpiderMightTalisman spider_might = new SpiderMightTalisman();
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
    @Config.Name("Target")
    public static Target target = new Target();









}