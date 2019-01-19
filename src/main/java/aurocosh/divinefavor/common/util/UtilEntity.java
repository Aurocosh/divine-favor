package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class UtilEntity {
    public static void addVelocity(Entity entity, Vec3d direction, float velocity){
        Vec3d motion = direction.normalize().scale(velocity);
        entity.motionX += motion.x;
        entity.motionY += motion.y;
        entity.motionZ += motion.z;
    }

    public static void tickLiquidWalk(EntityLivingBase entityLiving, Block liquid) {
        World world = entityLiving.getEntityWorld();
        BlockPos pos = entityLiving.getPosition();

        if (world.getBlockState(pos.down()).getBlock() != liquid)
            return;
        if (!world.isAirBlock(pos))
            return;
        if(entityLiving.motionY >= 0)
            return;

        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            if (player.isSneaking())
                return;
        }

        entityLiving.motionY = 0;
        entityLiving.onGround = true;
        entityLiving.setAIMoveSpeed(0.1F);
    }

    public static Vec3d toPlayerPosition(BlockPos pos) {
        return new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
//        return new Vec3d(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
    }

    public static void teleport(EntityLivingBase livingBase, GlobalBlockPos destination) {
        teleport(livingBase, destination.dimensionId, toPlayerPosition(destination.pos));
    }

    public static void teleport(EntityLivingBase livingBase, BlockPos destination) {
        teleport(livingBase, livingBase.getEntityWorld().provider.getDimension(), toPlayerPosition(destination));
    }

    public static void teleport(EntityLivingBase livingBase, Vec3d destination) {
        teleport(livingBase, livingBase.getEntityWorld().provider.getDimension(), destination);
    }

    public static void teleport(EntityLivingBase livingBase, int dimension, BlockPos destination) {
        teleport(livingBase, dimension, toPlayerPosition(destination));
    }

    public static void teleport(EntityLivingBase livingBase, int dimension, Vec3d destination) {
        float rotationYaw = livingBase.rotationYaw;
        float rotationPitch = livingBase.rotationPitch;

        int oldId = livingBase.getEntityWorld().provider.getDimension();
        if (oldId != dimension)
            teleportToDimension(livingBase, dimension, destination);

        livingBase.rotationYaw = rotationYaw;
        livingBase.rotationPitch = rotationPitch;
        livingBase.setPositionAndUpdate(destination.x, destination.y, destination.z);
    }

    public static void teleportToDimension(EntityLivingBase livingBase, int dimension, Vec3d destination) {
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) livingBase;
        MinecraftServer server = livingBase.getEntityWorld().getMinecraftServer();
        WorldServer worldServer = server.getWorld(dimension);
        if(livingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) livingBase;
            player.addExperienceLevel(0);
        }

        worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new CustomTeleporter(worldServer, destination));
        livingBase.setPositionAndUpdate(destination.x, destination.y, destination.z);
    }

    // client side only
    public static void addVelocity(EntityLivingBase entity, float factor) {
        if (entity.getRidingEntity() instanceof EntityLivingBase)
            entity = (EntityLivingBase) entity.getRidingEntity();
        if(entity.moveForward  <= 0)
            return;
        Vec3d extraVelocity = entity.getLookVec().scale(factor);
        entity.motionX += extraVelocity.x;
        entity.motionY += extraVelocity.y;
        entity.motionZ += extraVelocity.z;

//        entity.motionX += MathHelper.sin(-entity.rotationYaw * 0.017453292F) * factor;
//        entity.motionZ += MathHelper.cos(entity.rotationYaw * 0.017453292F) * factor;
    }
//

}
