package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.ropes.BarrierRope;
import aurocosh.divinefavor.common.config.ropes.ExplosiveRope;
import aurocosh.divinefavor.common.config.ropes.GuideRope;
import aurocosh.divinefavor.common.config.ropes.InertRope;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/ropes")
public class ConfigRope {
    @Config.Name("Barrier rope")
    public static BarrierRope barrierRope = new BarrierRope();
    @Config.Name("Explosive rppe")
    public static ExplosiveRope explosiveRope = new ExplosiveRope();
    @Config.Name("Guide rope")
    public static GuideRope guideRope = new GuideRope();
    @Config.Name("Inert rope")
    public static InertRope inertRope = new InertRope();
}