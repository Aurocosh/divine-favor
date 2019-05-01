package aurocosh.divinefavor.common.util;

import net.minecraft.util.math.Vec3d;

public class UtilVec3d {
    public static Vec3d normalize(Vec3d vec3d) {
        double x = vec3d.lengthSquared();
        double xHalf = 0.5d * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        x = Double.longBitsToDouble(i);
        x *= (1.5d - xHalf * x * x);
        return vec3d.scale(x);
    }
}
