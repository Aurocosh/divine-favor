package aurocosh.divinefavor.common.spirit.punishment

import aurocosh.divinefavor.common.config.common.ConfigPunishments
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class MateriaPunishment : SpiritPunishment() {
    override fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {
        destroyRandomBlocks(player, world, instance.multiBlockOrigin)
        placeRandomBlocks(player, world, instance.multiBlockOrigin)
    }

    private fun destroyRandomBlocks(player: EntityPlayer, world: World, center: BlockPos) {
        val radius = ConfigPunishments.materia.effectRadius
        val blocksToDestroy = ConfigPunishments.materia.blocksToDestroy.random()
        for (i in 0 until blocksToDestroy) {
            val blockPos = UtilCoordinates.getRandomBlockInRange(center, radius, BLOCK_SEARCH_LIMIT) { !world.isAirBlock(it) }
            if (blockPos != null)
                UtilBlock.removeBlock(player, world, ItemStack.EMPTY, blockPos, dropItem = false, isToolRequired = false, particles = false)
        }
    }

    private fun placeRandomBlocks(player: EntityPlayer, world: World, center: BlockPos) {
        val state = Blocks.STONE.defaultState
        val radius = ConfigPunishments.materia.effectRadius
        val blocksToPlace = ConfigPunishments.materia.blocksToPlace.random()
        for (i in 0 until blocksToPlace) {
            val blockPos = UtilCoordinates.getRandomBlockInRange(center, radius, BLOCK_SEARCH_LIMIT, world::isAirBlock)
            if (blockPos != null)
                UtilBlock.replaceBlock(player, world, blockPos, state)
        }
    }

    companion object {
        private const val BLOCK_SEARCH_LIMIT = 60
    }
}
