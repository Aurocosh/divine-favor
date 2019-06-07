package aurocosh.divinefavor.common.spirit.punishment

import aurocosh.divinefavor.common.config.common.ConfigPunishments
import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.distributed_random.DistributedRandomEntityList
import aurocosh.divinefavor.common.lib.extensions.filter
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.selectRandom
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment
import aurocosh.divinefavor.common.util.UtilAlgorithm
import aurocosh.divinefavor.common.util.UtilBlock
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class NeblazePunishment : SpiritPunishment() {

    override fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {
        player.setFire(ConfigPunishments.neblaze.ignitionTimeSeconds)
        smeltPartsOfAltar(player, world, instance)
        spawnEnemies(player, world)
        igniteRandomBlocks(player, world, instance.multiBlockOrigin)
    }

    private fun smeltPartsOfAltar(player: EntityPlayer, world: World, instance: MultiBlockInstance) {
        val blocksToSmelt = ConfigPunishments.neblaze.blocksToMelt.random()
        val solidsPositions = ArrayList(instance.positionsOfSolids)
        val netherrackPositions = solidsPositions.filter(world::getBlock, Blocks.NETHERRACK::equals)
        val selectedPositions = netherrackPositions.selectRandom(blocksToSmelt)

        val newState = Blocks.FLOWING_LAVA.defaultState
        for (position in selectedPositions) {
            if (UtilBlock.canBreakBlock(player, world, position, false)) {
//                UtilBlock.replaceBlock(player,world,position,newState)
                world.setBlockState(position, newState, 11)
            }
        }
    }

    private fun spawnEnemies(player: EntityPlayer, world: World) {
        val mobsToSummon = ConfigPunishments.neblaze.mobsToSpawn.random()
        val spawnAttempts = mobsToSummon * 10
        val playerPosition = player.position
        UtilAlgorithm.repeatUntilSuccessful({ spawnMob(world, playerPosition) }, mobsToSummon, spawnAttempts)
    }

    private fun spawnMob(world: World, pos: BlockPos): Boolean {
        val spawnRadius = ConfigPunishments.neblaze.spawnRadius
        val randomPos = UtilCoordinates.getRandomNeighbour(pos, spawnRadius, 0, spawnRadius)
        val spawnPos = UtilCoordinates.findPlaceToStand(randomPos, world, spawnRadius)
        if (spawnPos != null && possibleEnemies.size() > 0) {
            val clazz = possibleEnemies.random ?: return false
            return UtilEntity.spawnEntity(world, spawnPos, clazz)
        }
        return false
    }

    private fun igniteRandomBlocks(player: EntityPlayer, world: World, center: BlockPos) {
        val blocksToIgnite = ConfigPunishments.neblaze.blocksToIgnite.random()
        for (i in 0 until blocksToIgnite)
            igniteBlock(player, world, center)
    }

    private fun igniteBlock(player: EntityPlayer, world: World, center: BlockPos) {
        val ignitionRadius = ConfigPunishments.neblaze.ignitionRadius
        val ignitionPos = UtilCoordinates.getRandomBlockInRange(center, ignitionRadius, BLOCK_SEARCH_LIMIT) { pos -> !world.isAirBlock(pos) }
                ?: return

        BlockPosConstants.DIRECT_NEIGHBOURS.map(ignitionPos::add).first { pos -> UtilBlock.ignite(player, world, pos) }
    }

    companion object {
        val BLOCK_SEARCH_LIMIT = 60
        private val possibleEnemies = DistributedRandomEntityList(ConfigPunishments.neblaze.summonedEnemies)
    }
}
