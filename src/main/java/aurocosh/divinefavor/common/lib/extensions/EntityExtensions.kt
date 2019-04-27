package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.entity.Entity
import net.minecraft.util.math.Vec3d

fun Entity.getDistanceSq(pos: Vec3d): Double {
    return this.getDistanceSq(pos.x, pos.y, pos.z)
}
