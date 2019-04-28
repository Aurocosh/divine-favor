package aurocosh.divinefavor.common.spirit.punishment

import aurocosh.divinefavor.common.config.common.ConfigPunishments
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class RomolPunishment : SpiritPunishment() {

    override fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {
        destroyRandomBlocks(player, world, instance.multiBlockOrigin)
        breakRandomBlocks(player, world, instance.multiBlockOrigin)
    }

    private fun destroyRandomBlocks(player: EntityPlayer, world: World, center: BlockPos) {
        val radius = ConfigPunishments.romol.blockBreakingRadius
        val blocksToDestroy = ConfigPunishments.romol.blocksToDestroy.random()
        for (i in 0 until blocksToDestroy) {
            val blockPos = UtilCoordinates.getRandomBlockInRange(center, radius, BLOCK_SEARCH_LIMIT, world::isAirBlock)
            if (blockPos != null)
                UtilBlock.removeBlock(player, world, ItemStack.EMPTY, blockPos, false, false, false)
        }
    }

    private fun breakRandomBlocks(player: EntityPlayer, world: World, center: BlockPos) {
        val radius = ConfigPunishments.romol.blockBreakingRadius
        val blocksToBreak = ConfigPunishments.romol.blocksToBreak.random()
        for (i in 0 until blocksToBreak) {
            val blockPos = UtilCoordinates.getRandomBlockInRange(center, radius, BLOCK_SEARCH_LIMIT, world::isAirBlock)
            if (blockPos != null)
                UtilBlock.removeBlock(player, world, ItemStack.EMPTY, blockPos, true, false, false)
        }
    }

    companion object {
        val BLOCK_SEARCH_LIMIT = 60
    }
}
