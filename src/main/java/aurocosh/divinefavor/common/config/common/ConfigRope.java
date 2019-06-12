package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.ropes.*;
import net.minecraftforge.common.config.Config;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/ropes")
public class ConfigRope {
    @Config.Name("Barrier rope")
    public static BarrierRope barrierRope = new BarrierRope();
    @Config.Name("Explosive rppe")
    public static ExplosiveRope explosiveRope = new ExplosiveRope();
    @Config.Name("Guide rope")
    public static GuideRope guideRope = new GuideRope();
    @Config.Name("Inert rope")
    public static InertRope inertRope = new InertRope();
    @Config.Name("Luminous rope")
    public static LuminousRope luminousRope = new LuminousRope();
}