package aurocosh.divinefavor.common.block_operations.undo

import aurocosh.divinefavor.common.block_operations.`do`.BuildOperation
import aurocosh.divinefavor.common.tasks.BlockBreakingTask
import aurocosh.divinefavor.common.tasks.base.BaseTask
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class UndoBuild(private val coordinates: List<BlockPos>, operation: BuildOperation, blocksPerTick: Int) : UndoOperation(operation, blocksPerTick) {
    override fun getUndoTask(player: EntityPlayer, world: World): BaseTask {
        return BlockBreakingTask(coordinates, player, ItemStack.EMPTY, blocksPerTick)
    }
}