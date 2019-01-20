package aurocosh.divinefavor.common.custom_data.living.wind_leash;

import net.minecraft.util.math.Vec3d;

// The default implementation of the capability. Holds all the logic.
public class DefaultWindLeashHandler implements IWindLeashHandler {
    private Vec3d vector;
    private Vec3d vectorNormalized;

    public DefaultWindLeashHandler() {
        this.vector = Vec3d.ZERO;
        vectorNormalized = vector;
    }

    @Override
    public void setVector(Vec3d vector) {
        this.vector = vector;
        vectorNormalized = vector.normalize();
    }

    @Override
    public Vec3d getVector() {
        return vector;
    }

    @Override
    public Vec3d getNormalizedVector() {
        return vectorNormalized;
    }
}
