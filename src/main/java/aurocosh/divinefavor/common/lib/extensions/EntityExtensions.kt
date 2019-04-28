package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d

fun Entity.getDistanceSq(pos: Vec3d): Double {
    return this.getDistanceSq(pos.x, pos.y, pos.z)
}

fun Entity.getMotionVector(): Vec3d {
    return Vec3d(this.motionX, this.motionY, this.motionZ)
}

fun Entity.getPreviousPosition(): BlockPos {
    return BlockPos(this.prevPosX, this.prevPosY, this.prevPosZ)
}