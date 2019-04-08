package aurocosh.divinefavor.common.config.punishment;

import aurocosh.divinefavor.common.config.IntervalConfig;
import net.minecraftforge.common.config.Config;

public class RomolPunishmentConfig {
    @Config.Name("Blocks to destroy")
    public IntervalConfig blocksToDestroy = new IntervalConfig(10, 20);
    @Config.Name("Blocks to break")
    public IntervalConfig blocksToBreak = new IntervalConfig(30, 60);
    @Config.Name("Block breaking radius")
    public int blockBreakingRadius = 12;
}
