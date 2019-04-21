package aurocosh.divinefavor.common.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class UtilWorld {
    public static RayTraceResult raycast(World world, Vec3d origin, Vec3d ray, double length) {
        Vec3d end = origin.add(ray.normalize().scale(length));
        return world.rayTraceBlocks(origin, end);
    }

    public static PlayerList getPlayerList(World world) {
        MinecraftServer server = world.getMinecraftServer();
        assert server != null;
        return server.getPlayerList();
    }
}
