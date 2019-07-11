package aurocosh.divinefavor.common.undo

import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class UndoBuild(private val positions: List<BlockPos>) : UndoOperation() {
    override fun perform(player: EntityPlayer, world: World) {
        positions.forEach { UtilBlock.removeBlock(player, world, ItemStack.EMPTY, it, dropItem = true, isToolRequired = false, particles = false) }
    }
}