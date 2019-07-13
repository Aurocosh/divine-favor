package aurocosh.divinefavor.common.tasks

import aurocosh.divinefavor.common.tasks.base.ServerSideTask
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.*
import kotlin.math.min

class BlockPlacingServerTask(blocksToPlace: List<BlockPos>, private val state: IBlockState, world: World, private val blocksPerTick: Int) : ServerSideTask(world) {
    private val blocksToPlace: Queue<BlockPos>

    init {
        this.blocksToPlace = LinkedList(blocksToPlace)
    }

    @SubscribeEvent
    fun blockPlace(event: TickEvent.WorldTickEvent) {
        if (!isSameDimension(event.world))
            return

        var breakCount = min(blocksPerTick, blocksToPlace.size)
        while (breakCount-- > 0) {
            val pos = blocksToPlace.remove()
            world.setBlockState(pos, state)
        }
        if (blocksToPlace.isEmpty())
            finish()
    }
}