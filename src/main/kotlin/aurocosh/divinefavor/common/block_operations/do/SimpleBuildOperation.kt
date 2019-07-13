package aurocosh.divinefavor.common.block_operations.`do`

import aurocosh.divinefavor.common.block_operations.undo.UndoBuild
import aurocosh.divinefavor.common.block_operations.undo.UndoOperation
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.tasks.BlockBuildData
import aurocosh.divinefavor.common.tasks.BlockBuildingTask
import aurocosh.divinefavor.common.tasks.base.BaseTask
import aurocosh.divinefavor.common.util.UtilPlayer
import aurocosh.divinefavor.common.util.UtilTick
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class SimpleBuildOperation(private val coordinates: List<BlockPos>, private val state: IBlockState) : BuildOperation() {
    override fun getBuildingTask(player: EntityPlayer, world: World): Pair<BaseTask, UndoOperation> {
        val toConsume = coordinates.count()
        val blocksConsumed = UtilPlayer.consumeBlocks(player, world, state, toConsume, false)
        val gooToConsume = toConsume - blocksConsumed
        val gooConsumed = UtilPlayer.consumeGoo(player, gooToConsume, false)

        val realBlocks = coordinates.subList(0, blocksConsumed).S
                .map { BlockBuildData(it, true) }
        val gooBlocks = coordinates.subList(blocksConsumed, blocksConsumed + gooConsumed).S
                .map { BlockBuildData(it, false) }

        val buildData = (realBlocks + gooBlocks).toList().shuffled()

        val buildTime = UtilTick.secondsToTicks(2f)
        val blocksPerTick = if (buildTime > buildData.size) 1 else buildData.size / buildTime
        val blockPlacingTask = BlockBuildingTask(buildData, state, player, blocksPerTick)
        val undoBuild = UndoBuild(coordinates, this, blocksPerTick)
        return Pair(blockPlacingTask, undoBuild)
    }
}