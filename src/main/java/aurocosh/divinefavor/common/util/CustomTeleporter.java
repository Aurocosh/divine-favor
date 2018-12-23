package aurocosh.divinefavor.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CustomTeleporter extends Teleporter {
    private final WorldServer worldServerInstance;
    private Vec3d destination;

    public CustomTeleporter(WorldServer world, Vec3d destination) {
        super(world);
        this.worldServerInstance = world;
        this.destination = destination;
    }

    @Override
    public void placeInPortal(Entity pEntity, float rotationYaw) {
        this.worldServerInstance.getBlockState(new BlockPos(destination));
        pEntity.setPosition(destination.x, destination.y, destination.z);
        pEntity.motionX = 0.0f;
        pEntity.motionY = 0.0f;
        pEntity.motionZ = 0.0f;
    }
}
