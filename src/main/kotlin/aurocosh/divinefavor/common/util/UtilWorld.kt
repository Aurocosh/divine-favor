package aurocosh.divinefavor.common.util

import net.minecraft.server.management.PlayerList
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

object UtilWorld {
    fun raycast(world: World, origin: Vec3d, ray: Vec3d, length: Double): RayTraceResult? {
        val end = origin.add(ray.normalize().scale(length))
        return world.rayTraceBlocks(origin, end)
    }

    fun getPlayerList(world: World): PlayerList {
        return world.minecraftServer!!.playerList
    }
}
