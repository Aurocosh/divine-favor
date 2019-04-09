package aurocosh.divinefavor.common.config.punishment;

import aurocosh.divinefavor.common.config.data.IntInterval;
import net.minecraftforge.common.config.Config;

public class EnderererPunishmentConfig {
    @Config.Name("Blocks to move")
    public IntInterval blocksToMove = new IntInterval(20, 40);
    @Config.Name("Player teleport radius")
    public int playerTeleportRadius = 30;
    @Config.Name("Block teleport radius")
    public int blockTeleportRadius = 15;
}
