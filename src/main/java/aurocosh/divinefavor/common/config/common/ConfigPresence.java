package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.presences.*;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/presence")
public class ConfigPresence {
    @Config.Name("Chilling presence")
    public static ChillingPresence chillingPresence = new ChillingPresence();
    @Config.Name("Energetic presence")
    public static EnergeticPresence energeticPresence = new EnergeticPresence();
    @Config.Name("Furious presence")
    public static FuriousPresence furiousPresence = new FuriousPresence();
    @Config.Name("Industrious presence")
    public static IndustriousPresence industriousPresence = new IndustriousPresence();
    @Config.Name("Manipulative presence")
    public static ManipulativePresence manipulativePresence = new ManipulativePresence();
    @Config.Name("Predatory presence")
    public static PredatoryPresence predatoryPresence = new PredatoryPresence();
    @Config.Name("Scorching presence")
    public static ScorchingPresence scorchingPresence = new ScorchingPresence();
    @Config.Name("Towering presence")
    public static ToweringPresence toweringPresence = new ToweringPresence();
    @Config.Name("Warping presence")
    public static WarpingPresence warpingPresence = new WarpingPresence();
}