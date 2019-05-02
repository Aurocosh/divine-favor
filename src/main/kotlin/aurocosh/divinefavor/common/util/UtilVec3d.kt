package aurocosh.divinefavor.common.util

import net.minecraft.util.math.Vec3d

object UtilVec3d {
    fun normalize(vec3d: Vec3d): Vec3d {
        var x = vec3d.lengthSquared()
        val xHalf = 0.5 * x
        var i = java.lang.Double.doubleToLongBits(x)
        i = 0x5fe6ec85e7de30daL - (i shr 1)
        x = java.lang.Double.longBitsToDouble(i)
        x *= 1.5 - xHalf * x * x
        return vec3d.scale(x)
    }
}
