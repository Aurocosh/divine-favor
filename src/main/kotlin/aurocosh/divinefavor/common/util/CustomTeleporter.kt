package aurocosh.divinefavor.common.util

import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.Teleporter
import net.minecraft.world.WorldServer

class CustomTeleporter(private val worldServer: WorldServer, private val destination: Vec3d) : Teleporter(worldServer) {
    override fun placeInPortal(entity: Entity, rotationYaw: Float) {
        this.worldServer.getBlockState(BlockPos(destination))
        entity.setPosition(destination.x, destination.y, destination.z)
        entity.motionX = 0.0
        entity.motionY = 0.0
        entity.motionZ = 0.0
    }
}
