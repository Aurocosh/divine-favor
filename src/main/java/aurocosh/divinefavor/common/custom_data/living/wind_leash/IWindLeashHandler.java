package aurocosh.divinefavor.common.custom_data.living.wind_leash;

import net.minecraft.util.math.Vec3d;

public interface IWindLeashHandler {
    void setVector(Vec3d vector);
    Vec3d getVector();
    Vec3d getNormalizedVector();
}
