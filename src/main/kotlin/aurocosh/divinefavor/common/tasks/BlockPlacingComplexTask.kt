package aurocosh.divinefavor.common.tasks

import aurocosh.divinefavor.common.tasks.base.ServerSideTask
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.*
import kotlin.math.min

open class BlockPlacingComplexTask(blocksToPlace: List<Pair<BlockPos, IBlockState>>, private val player: EntityPlayer, private val blocksPerTick: Int) : ServerSideTask(player.world) {
    private val blocksToPlace: Queue<Pair<BlockPos, IBlockState>> = LinkedList(blocksToPlace)

    @SubscribeEvent
    fun blockPlace(event: TickEvent.WorldTickEvent) {
        if (!isSameDimension(event.world))
            return

        var breakCount = min(blocksPerTick, blocksToPlace.size)
        while (breakCount-- > 0) {
            val (pos, state) = blocksToPlace.remove()
            UtilBlock.replaceBlock(player, world, pos, state)
        }
        if (blocksToPlace.isEmpty())
            finish()
    }
}