package aurocosh.divinefavor.common.tasks

import aurocosh.divinefavor.common.tasks.base.ServerSideTask
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.util.*

class BlockReplacingTask(blocksToPlace: List<BlockPos>, private val state: IBlockState, private val player: EntityPlayer, private val blocksPerTick: Int) : ServerSideTask(player.world) {
    private val blocksToPlace: Queue<BlockPos>

    init {
        this.blocksToPlace = LinkedList(blocksToPlace)
    }

    @SubscribeEvent
    fun blockPlace(event: TickEvent.WorldTickEvent) {
        if (!isSameDimension(event.world))
            return

        var breakCount = Math.min(blocksPerTick, blocksToPlace.size)
        while (breakCount-- > 0) {
            val pos = blocksToPlace.remove()
            UtilBlock.removeBlock(player, world, ItemStack.EMPTY, pos, true, false,false)
            UtilBlock.replaceBlock(player, world, pos, state)
        }
        if (blocksToPlace.isEmpty())
            finish()
    }
}