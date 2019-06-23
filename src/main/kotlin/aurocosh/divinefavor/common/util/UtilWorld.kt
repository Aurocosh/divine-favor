package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.potions.potions.PotionNightEye
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import net.minecraft.server.management.PlayerList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.EnumSkyBlock
import net.minecraft.world.World

object UtilWorld {
    fun raycast(world: World, origin: Vec3d, ray: Vec3d, length: Double): RayTraceResult? {
        val end = origin.add(ray.normalize().scale(length))
        return world.rayTraceBlocks(origin, end)
    }

    fun getPlayerList(world: World): PlayerList {
        return world.minecraftServer!!.playerList
    }

    fun getLightLevel(world: World, pos: BlockPos): Int {
        val skyLightSub = world.calculateSkylightSubtracted(1.0f)
        val lightBlock = world.getLightFor(EnumSkyBlock.BLOCK, pos)
        val lightSky = world.getLightFor(EnumSkyBlock.SKY, pos) - skyLightSub
        return Math.max(lightBlock, lightSky)
    }
}
