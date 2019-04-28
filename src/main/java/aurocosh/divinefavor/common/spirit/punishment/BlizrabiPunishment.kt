package aurocosh.divinefavor.common.spirit.punishment

import aurocosh.divinefavor.common.config.common.ConfigPunishments
import aurocosh.divinefavor.common.muliblock.instance.MultiBlockInstance
import aurocosh.divinefavor.common.potions.base.effect.ModEffect
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment
import aurocosh.divinefavor.common.util.UtilAlgorithm
import aurocosh.divinefavor.common.util.UtilCoordinates
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.passive.EntityWolf
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BlizrabiPunishment : SpiritPunishment() {
    override fun execute(player: EntityPlayer, world: World, pos: BlockPos, state: IBlockState, instance: MultiBlockInstance) {
        player.addPotionEffect(ModEffect(MobEffects.SLOWNESS, ConfigPunishments.blizrabi.slownessDuration, ConfigPunishments.blizrabi.slownessAmplifier).setIsCurse())
        player.addPotionEffect(ModEffect(MobEffects.WEAKNESS, ConfigPunishments.blizrabi.weaknessDuration, ConfigPunishments.blizrabi.weaknessAmplifier).setIsCurse())

        val wolfsToSpawn = ConfigPunishments.blizrabi.wolfsToSpawn.random()
        val spawnAttempts = wolfsToSpawn * 10
        UtilAlgorithm.repeatUntilSuccessful({ spawnWolf(player, world) }, wolfsToSpawn, spawnAttempts)
    }

    private fun spawnWolf(player: EntityPlayer, world: World): Boolean {
        val spawnRadius = ConfigPunishments.blizrabi.spawnRadius
        val firstGuess: BlockPos = UtilCoordinates.getRandomNeighbour(player.position, spawnRadius, 0, spawnRadius)
        val spawnPos = UtilCoordinates.findPlaceToStand(firstGuess, world, spawnRadius) ?: return false

        val entityWolf = EntityWolf(world)
        entityWolf.setLocationAndAngles(spawnPos.x.toDouble(), spawnPos.y.toDouble(), spawnPos.z.toDouble(), 0f, 0.0f)

        entityWolf.attackTarget = player
        entityWolf.isAngry = true
        world.spawnEntity(entityWolf)
        return true
    }
}
