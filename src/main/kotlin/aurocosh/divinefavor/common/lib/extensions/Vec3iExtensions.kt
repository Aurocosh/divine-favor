package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i

fun Vec3i.toVec3d(): Vec3d {
    return Vec3d(this)
}

fun Vec3i.toBlockPos(): BlockPos {
    return BlockPos(this)
}