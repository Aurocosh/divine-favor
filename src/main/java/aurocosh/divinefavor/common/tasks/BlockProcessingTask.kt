package aurocosh.divinefavor.common.tasks

import aurocosh.divinefavor.common.tasks.base.ServerSideTask
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.*

class BlockProcessingTask(blocks: List<BlockPos>, world: World, private val blocksPerTick: Int, private val processor: (BlockPos) -> Unit) : ServerSideTask(world) {
    private val blocks: Queue<BlockPos>

    init {
        this.blocks = LinkedList(blocks)
    }

    @SubscribeEvent
    fun blockBreak(event: TickEvent.WorldTickEvent) {
        if (!isSameDimension(event.world))
            return

        var breakCount = Math.min(blocksPerTick, blocks.size)
        while (breakCount-- > 0)
            processor.invoke(blocks.remove())
        if (blocks.isEmpty())
            finish()
    }
}