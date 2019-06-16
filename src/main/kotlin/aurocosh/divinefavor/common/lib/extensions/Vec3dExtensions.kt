package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.util.EnumFacing
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i

fun Vec3d.add(vec3i: Vec3i): Vec3d {
    return this.add(vec3i.x.toDouble(), vec3i.y.toDouble(), vec3i.z.toDouble())
}

fun Vec3d.toFacing(): EnumFacing {
    return EnumFacing.getFacingFromVector(this.x.toFloat(), this.y.toFloat(), this.z.toFloat())
}
