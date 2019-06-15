package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.entity.Entity
import net.minecraft.util.DamageSource
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

fun Entity.attackEntityNoTimer(source: DamageSource, amount: Float): Boolean {
    this.hurtResistantTime = 0
    return this.attackEntityFrom(source, amount)
}

fun Entity.getPartialPosition(partialTick: Float): Vec3d {
    return Vec3d(
            this.lastTickPosX + (this.posX - this.lastTickPosX) * partialTick,
            this.lastTickPosY + (this.posY - this.lastTickPosY) * partialTick,
            this.lastTickPosZ + (this.posZ - this.lastTickPosZ) * partialTick
    )
}