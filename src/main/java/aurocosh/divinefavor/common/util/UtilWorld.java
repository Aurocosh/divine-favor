package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.lib.math.Vector3;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class UtilWorld {
    public static RayTraceResult raycast(World world, Vector3 origin, Vector3 ray, double len) {
        Vector3 end = origin.copy().add(ray.copy().normalize().multiply(len));
        return world.rayTraceBlocks(origin.toVec3D(), end.toVec3D());
    }
}