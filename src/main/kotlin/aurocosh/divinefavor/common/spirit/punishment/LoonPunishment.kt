package aurocosh.divinefavor.common.spirit.punishment

import aurocosh.divinefavor.common.config.common.ConfigPunishments
import aurocosh.divinefavor.common.lib.distributed_random.DistributedRandomEntityList
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment
import aurocosh.divinefavor.common.tasks.base.ServerSideTask
import aurocosh.divinefavor.common.util.UtilAlgorithm
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class LoonPunishment : SpiritPunishment() {
    override fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {
        val task = LoonPunishmentTask(player)
        task.start()
    }

    class LoonPunishmentTask(private val player: EntityPlayer) : ServerSideTask(player.world) {
        private var waveCount: Int = 0
        private var waveDelay: Int = 0

        init {
            waveCount = ConfigPunishments.loon.waveCount.random()
            waveDelay = 0
        }

        @SubscribeEvent
        fun tickEvent(event: TickEvent.WorldTickEvent) {
            if (!isSameDimension(event.world))
                return

            if (waveDelay-- > 0)
                return
            waveDelay = ConfigPunishments.loon.waveDelay.random()

            val mobsToSpawn = ConfigPunishments.loon.enemiesPerWave.random()
            val spawnAttempts = mobsToSpawn * 10
            val playerPosition = player.position
            UtilAlgorithm.repeatUntilSuccessful({ spawnMob(world, playerPosition) }, mobsToSpawn, spawnAttempts)

            if (waveCount-- <= 0)
                finish()
        }

        @SubscribeEvent
        fun onPlayerLogout(event: PlayerEvent.PlayerLoggedOutEvent) {
            if (event.player === player)
                finish()
        }

        private fun spawnMob(world: World, pos: BlockPos): Boolean {
            val spawnRadius = ConfigPunishments.loon.spawnRadius
            val randomPos = UtilCoordinates.getRandomNeighbour(pos, spawnRadius, 0, spawnRadius)
            val spawnPos = UtilCoordinates.findPlaceToStand(randomPos, world, spawnRadius)
            if (spawnPos != null && possibleEnemies.size() > 0) {
                val clazz = possibleEnemies.random ?: return false
                return UtilEntity.spawnEntity(world, spawnPos, clazz)
            }
            return false
        }

        companion object {
            private val possibleEnemies = DistributedRandomEntityList(ConfigPunishments.loon.summonedEnemies)
        }
    }
}
