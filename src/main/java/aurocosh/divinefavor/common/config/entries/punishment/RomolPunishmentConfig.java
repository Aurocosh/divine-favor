package aurocosh.divinefavor.common.config.entries.punishment;

import aurocosh.divinefavor.common.config.data.IntInterval;
import net.minecraftforge.common.config.Config;

public class RomolPunishmentConfig {
    @Config.Name("Blocks to destroy")
    public IntInterval blocksToDestroy = new IntInterval(10, 20);
    @Config.Name("Blocks to break")
    public IntInterval blocksToBreak = new IntInterval(30, 60);
    @Config.Name("Block breaking radius")
    public int blockBreakingRadius = 12;
}
