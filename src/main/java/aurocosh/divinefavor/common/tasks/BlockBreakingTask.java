package aurocosh.divinefavor.common.tasks;

import aurocosh.divinefavor.common.tasks.base.ServerSideTask;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BlockBreakingTask extends ServerSideTask {
    private final ItemStack tool;
    private final int blocksPerTick;
    private final EntityPlayer player;
    private final Queue<BlockPos> blocksToRemove;

    public BlockBreakingTask(List<BlockPos> blocksToRemove, EntityPlayer player, ItemStack tool, int blocksPerTick) {
        super(player.world);
        this.player = player;
        this.tool = tool;
        this.blocksPerTick = blocksPerTick;
        this.blocksToRemove = new LinkedList<>(blocksToRemove);
    }

    @SubscribeEvent
    public void blockBreak(TickEvent.WorldTickEvent event) {
        if (!isSameDimension(event.world))
            return;

        int breakCount = Math.min(blocksPerTick, blocksToRemove.size());
        while (breakCount-- > 0)
            UtilBlock.removeBlock(player, world, tool, blocksToRemove.remove(), true, false, false);
        if (blocksToRemove.isEmpty())
            finish();
    }
}