package aurocosh.divinefavor.common.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;

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
        return new Vec3d(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
    }

    public static void teleport(EntityPlayer player, BlockPos destination) {
        teleport(player, player.getEntityWorld().provider.getDimension(), toPlayerPosition(destination));
    }

    public static void teleport(EntityPlayer player, Vec3d destination) {
        teleport(player, player.getEntityWorld().provider.getDimension(), destination);
    }

    public static void teleport(EntityPlayer player, int dimension, BlockPos destination) {
        teleport(player, dimension, toPlayerPosition(destination));
    }

    public static void teleport(EntityPlayer player, int dimension, Vec3d destination) {
        float rotationYaw = player.rotationYaw;
        float rotationPitch = player.rotationPitch;

        int oldId = player.getEntityWorld().provider.getDimension();
        if (oldId != dimension)
            teleportToDimension(player, dimension, destination);

        player.rotationYaw = rotationYaw;
        player.rotationPitch = rotationPitch;
        player.setPositionAndUpdate(destination.x, destination.y, destination.z);
    }

    public static void teleportToDimension(EntityPlayer player, int dimension, Vec3d destination) {
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        MinecraftServer server = player.getEntityWorld().getMinecraftServer();
        WorldServer worldServer = server.getWorld(dimension);
        player.addExperienceLevel(0);

        worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new CustomTeleporter(worldServer, destination));
        player.setPositionAndUpdate(destination.x, destination.y, destination.z);
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
}
