package aurocosh.divinefavor.common.tasks;

import aurocosh.divinefavor.common.tasks.base.ServerSideTask;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BlockProcessingTask extends ServerSideTask {
    private final int blocksPerTick;
    private final Queue<BlockPos> blocks;
    private final UtilList.Processor<BlockPos> processor;

    public BlockProcessingTask(List<BlockPos> blocks, World world, int blocksPerTick, UtilList.Processor<BlockPos> processor) {
        super(world);
        this.blocksPerTick = blocksPerTick;
        this.blocks = new LinkedList<>(blocks);
        this.processor = processor;
    }

    @SubscribeEvent
    public void blockBreak(TickEvent.WorldTickEvent event) {
        if (!isSameDimension(event.world))
            return;

        int breakCount = Math.min(blocksPerTick, blocks.size());
        while (breakCount-- > 0)
            processor.process(blocks.remove());
        if (blocks.isEmpty())
            finish();
    }
}