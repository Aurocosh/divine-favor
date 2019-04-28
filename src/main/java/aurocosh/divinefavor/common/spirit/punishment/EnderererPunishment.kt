package aurocosh.divinefavor.common.spirit.punishment

import aurocosh.divinefavor.common.config.common.ConfigPunishments
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.isAirOrReplacable
import aurocosh.divinefavor.common.lib.extensions.selectRandom
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class EnderererPunishment : SpiritPunishment() {
    override fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {
        val radius = ConfigPunishments.endererer.playerTeleportRadius
        val destination = UtilCoordinates.getRandomNeighbourSafe(pos, radius, radius, radius)
        UtilEntity.teleport(player, destination)

        teleportPartsOfAltar(player, world, instance)
    }

    private fun teleportPartsOfAltar(player: EntityPlayer, world: World, instance: MultiBlockInstance) {
        val blocksToMove = ConfigPunishments.endererer.blocksToMove.random()
        val solidsPositions = ArrayList(instance.positionsOfSolids)
        val coalPositions = solidsPositions.filter(world::getBlock, Blocks.COAL_BLOCK::equals)
        val selectedPositions = coalPositions.selectRandom(blocksToMove)
        for (position in selectedPositions)
            swapBlocks(player, world, position)
    }

    private fun swapBlocks(player: EntityPlayer, world: World, position: BlockPos) {
        if (!(world.isAirBlock(position) || UtilBlock.canBreakBlock(player, world, position, false)))
            return
        val blockTeleportRadius = ConfigPunishments.endererer.blockTeleportRadius
        val destination = UtilCoordinates.getRandomNeighbourSafe(position, blockTeleportRadius, blockTeleportRadius, blockTeleportRadius)
        if (!(world.isAirOrReplacable(destination) || UtilBlock.canBreakBlock(player, world, destination, false)))
            return
        val positionState = world.getBlockState(position)
        val destinationState = world.getBlockState(destination)

        UtilBlock.replaceBlock(player, world, position, destinationState)
        UtilBlock.replaceBlock(player, world, destination, positionState)
    }
}
