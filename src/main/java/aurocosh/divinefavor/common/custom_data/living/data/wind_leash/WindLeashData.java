package aurocosh.divinefavor.common.custom_data.living.data.wind_leash;

import net.minecraft.util.math.Vec3d;

public class WindLeashData {
    private Vec3d vector;
    private Vec3d vectorNormalized;

    public WindLeashData() {
        this.vector = Vec3d.ZERO;
        vectorNormalized = vector;
    }

    public void setVector(Vec3d vector) {
        this.vector = vector;
        vectorNormalized = vector.normalize();
    }

    public Vec3d getVector() {
        return vector;
    }

    public Vec3d getNormalizedVector() {
        return vectorNormalized;
    }
}
