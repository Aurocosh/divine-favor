package aurocosh.divinefavor.common.config.common;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.entries.minions.*;
import net.minecraftforge.common.config.Config;

@Config(modid = DivineFavor.MOD_ID, name = DivineFavor.MOD_ID + "/minions")
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
    @Config.Name("Spider minion")
    public static MinionSpider spider = new MinionSpider();
    @Config.Name("Cave spider minion")
    public static MinionCaveSpider caveSpider = new MinionCaveSpider();
}