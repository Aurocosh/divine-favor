package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.common.config.minions.*;
import aurocosh.divinefavor.common.constants.ConstMisc;
import net.minecraftforge.common.config.Config;

@Config(modid = ConstMisc.MOD_ID, name = ConstMisc.MOD_ID + "/minions")
public class ConfigMinion {
    @Config.Name("Creeper minion")
    public static MinionCreeper creeper = new MinionCreeper();
    @Config.Name("Husk minion")
    public static MinionHusk husk = new MinionHusk();
    @Config.Name("Skeleton minion")
    public static MinionSkeleton skeleton = new MinionSkeleton();
    @Config.Name("Stray minion")
    public static MinionStray stray = new MinionStray();
    @Config.Name("Zombie minion")
    public static MinionZombie zombie = new MinionZombie();
}