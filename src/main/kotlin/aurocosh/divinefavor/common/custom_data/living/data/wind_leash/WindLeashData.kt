package aurocosh.divinefavor.common.custom_data.living.data.wind_leash

import net.minecraft.util.math.Vec3d

class WindLeashData {
    var vector: Vec3d = Vec3d.ZERO
        set(value) {
            field = value
            normalizedVector = value.normalize()
        }

    var normalizedVector: Vec3d = Vec3d.ZERO
        private set
}
