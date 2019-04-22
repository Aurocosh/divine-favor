package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.entries.auras.*;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/aura")
public class ConfigAura {
    @Config.Name("Arboreal aura")
    public static ArborealAura arborealAura = new ArborealAura();
    @Config.Name("Calling aura")
    public static CallingAura callingAura = new CallingAura();
    @Config.Name("Charred aura")
    public static CharredAura charredAura = new CharredAura();
    @Config.Name("Distorted aura")
    public static DistortedAura distortedAura = new DistortedAura();
    @Config.Name("Energetic aura")
    public static EnergeticAura energeticAura = new EnergeticAura();
    @Config.Name("Frosty aura")
    public static FrostyAura frostyAura = new FrostyAura();
    @Config.Name("Hunters aura")
    public static HuntersAura huntersAura = new HuntersAura();
    @Config.Name("Mineral aura")
    public static MineralAura mineralAura = new MineralAura();
    @Config.Name("Visceral aura")
    public static VisceralAura visceralAura = new VisceralAura();
}