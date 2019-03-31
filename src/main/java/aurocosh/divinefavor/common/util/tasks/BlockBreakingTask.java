package aurocosh.divinefavor.common.util.tasks;

import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BlockBreakingTask {
    private final World world;
    private final ItemStack tool;
    private final int blocksPerTick;
    private final EntityPlayer player;
    private final Queue<BlockPos> blocksToRemove;

    public BlockBreakingTask(List<BlockPos> blocksToRemove, EntityPlayer player, ItemStack tool, int blocksPerTick) {
        this.world = player.getEntityWorld();
        this.player = player;
        this.tool = tool;
        this.blocksPerTick = blocksPerTick;
        this.blocksToRemove = new LinkedList<>(blocksToRemove);
    }

    @SubscribeEvent
    public void blockBreak(TickEvent.WorldTickEvent event) {
        if (event.side.isClient()) {
            finish();
            return;
        }

        // only if same dimension
        if (event.world.provider.getDimension() != world.provider.getDimension())
            return;

        int breakCount = Math.min(blocksPerTick, blocksToRemove.size());
        while (breakCount-- > 0)
            UtilBlock.removeBlockWithDrops(player, world, tool, blocksToRemove.remove(), false, false);
        if (blocksToRemove.isEmpty())
            finish();
    }

    private void finish() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}