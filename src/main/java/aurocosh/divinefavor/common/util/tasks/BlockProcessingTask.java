package aurocosh.divinefavor.common.util.tasks;

import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BlockProcessingTask {

    private final World world;
    private final int blocksPerTick;
    private final Queue<BlockPos> blocks;
    private final UtilList.Processor<BlockPos> processor;

    public BlockProcessingTask(List<BlockPos> blocks, World world, int blocksPerTick, UtilList.Processor<BlockPos> processor) {
        this.world = world;
        this.blocksPerTick = blocksPerTick;
        this.blocks = new LinkedList<>(blocks);
        this.processor = processor;
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

        int breakCount = Math.min(blocksPerTick, blocks.size());
        while (breakCount-- > 0)
            processor.process(blocks.remove());
        if (blocks.isEmpty())
            finish();
    }

    private void finish() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}