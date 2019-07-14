package aurocosh.divinefavor.common.config.entries.punishment;

import aurocosh.divinefavor.common.config.data.IntInterval;
import net.minecraftforge.common.config.Config;

public class MateriaPunishmentConfig {
    @Config.Name("Blocks to destroy")
    public IntInterval blocksToDestroy = new IntInterval(10, 20);
    @Config.Name("Blocks to place")
    public IntInterval blocksToPlace = new IntInterval(30, 60);
    @Config.Name("Block breaking radius")
    public int effectRadius = 12;
}
