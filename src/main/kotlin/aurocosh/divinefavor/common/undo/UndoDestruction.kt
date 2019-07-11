package aurocosh.divinefavor.common.undo

import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class UndoDestruction(private val positions: List<Pair<BlockPos, IBlockState>>) : UndoOperation() {
    override fun perform(player: EntityPlayer, world: World) {
        positions.S
                .filter { (pos) -> world.isAirBlock(pos) }
                .forEach { (pos, state) -> UtilBlock.replaceBlock(player, world, pos, state) }
    }
}