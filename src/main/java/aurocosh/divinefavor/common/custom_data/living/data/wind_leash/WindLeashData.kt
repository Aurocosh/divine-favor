package aurocosh.divinefavor.common.custom_data.living.data.wind_leash

import net.minecraft.util.math.Vec3d

class WindLeashData {
    private var vector: Vec3d = Vec3d.ZERO
    var normalizedVector: Vec3d = Vec3d.ZERO
        private set

    fun setVector(vector: Vec3d) {
        this.vector = vector
        normalizedVector = vector.normalize()
    }

    fun getVector(): Vec3d {
        return vector
    }
}
