package aurocosh.divinefavor.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class UtilEntity {
    public static void addVelocity(Entity entity, Vec3d direction, float velocity){
        Vec3d motion = direction.normalize().scale(velocity);
        entity.motionX += motion.x;
        entity.motionY += motion.y;
        entity.motionZ += motion.z;
    }
}
