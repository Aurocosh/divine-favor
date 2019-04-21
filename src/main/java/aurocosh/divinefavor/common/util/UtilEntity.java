package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.lib.GlobalBlockPos;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public class UtilEntity {
    public static void setVelocity(Entity entity, Vec3d direction, float velocity) {
        Vec3d motion = direction.normalize().scale(velocity);
        entity.motionX = motion.x;
        entity.motionY = motion.y;
        entity.motionZ = motion.z;
    }

    public static void addVelocity(Entity entity, Vec3d direction, float velocity) {
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
        if (entityLiving.motionY >= 0)
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

    public static void dropItemsOnGround(World world, IItemHandler handler, BlockPos pos) {
        if (handler != null)
            for (int i = 0; i < handler.getSlots(); i++)
                dropItemOnGround(world, handler.getStackInSlot(i), pos);
    }

    public static void dropItemOnGround(World world, ItemStack stack, BlockPos pos) {
        if (!stack.isEmpty())
            world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
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
        if (livingBase instanceof EntityPlayer) {
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
        if (entity.moveForward <= 0)
            return;
        Vec3d extraVelocity = entity.getLookVec().scale(factor);
        entity.motionX += extraVelocity.x;
        entity.motionY += extraVelocity.y;
        entity.motionZ += extraVelocity.z;

//        entity.motionX += MathHelper.sin(-entity.rotationYaw * 0.017453292F) * factor;
//        entity.motionZ += MathHelper.cos(entity.rotationYaw * 0.017453292F) * factor;
    }

    public static RayTraceResult getBlockPlayerLookingAt(EntityPlayer player, double length) {
        return UtilWorld.raycast(player.world, player.getPositionEyes(0), player.getLookVec(), length);
    }

    public static <T extends Entity> List<T> getNearbyEntities(Class<? extends T> clazz, World world, Vec3d origin, double radius, @Nullable Predicate<? super T> filter) {
        AxisAlignedBB axis = new AxisAlignedBB(origin.x - radius, origin.y - radius, origin.z - radius, origin.x + radius, origin.y + radius, origin.z + radius);
        return world.getEntitiesWithinAABB(clazz, axis, filter);
    }

    public static <T extends Entity> List<T> getNearbyEntities(Class<? extends T> clazz, Entity entity, double radius, @Nullable Predicate<? super T> filter) {
        AxisAlignedBB axis = entity.getEntityBoundingBox().grow(radius);
        return entity.world.getEntitiesWithinAABB(clazz, axis, filter);
    }

    public static boolean isInRadius(Vec3d origin, Entity entity, double radiusSq) {
        return origin.squareDistanceTo(entity.getPositionVector()) < radiusSq;
    }

    public static boolean isInCone(Vec3d playerLookVec, Vec3d origin, Entity entity, double coneTolerance) {
        Vec3d vec = entity.getPositionVector().subtract(origin).normalize();
        return vec.dotProduct(playerLookVec) >= coneTolerance;
    }

    public static <T extends Entity> T getEntityPlayerLookingAt(EntityPlayer player, Class<? extends T> clazz, double searchDistance, boolean ignoreMount) {
        Vec3d entityLook = player.getLook(1);
        Vec3d positionEyes = player.getPositionEyes(1);
        Vec3d rayEnd = positionEyes.add(entityLook.x * searchDistance, entityLook.y * searchDistance, entityLook.z * searchDistance);

        AxisAlignedBB searchBoundingBox = player.getEntityBoundingBox().expand(entityLook.x * searchDistance, entityLook.y * searchDistance, entityLook.z * searchDistance).grow(1.0D, 1.0D, 1.0D);
        Predicate<T> predicate = Predicates.and(EntitySelectors.NOT_SPECTATING, e -> (e != null) && (e != player) && e.canBeCollidedWith() && (ignoreMount || (e.getLowestRidingEntity() == player.getLowestRidingEntity())));
        List<T> entities = player.world.getEntitiesWithinAABB(clazz, searchBoundingBox, predicate);

        T pointedEntity = null;
        double hitDistance = searchDistance;
        for (T entity : entities) {
            AxisAlignedBB boundingBox = entity.getEntityBoundingBox().grow(entity.getCollisionBorderSize());
            RayTraceResult rayTraceResult = boundingBox.calculateIntercept(positionEyes, rayEnd);

            if (boundingBox.contains(positionEyes)) {
                if (hitDistance >= 0.0D) {
                    pointedEntity = entity;
                    break;
                }
            }
            else if (rayTraceResult != null) {
                double newHitDistance = positionEyes.distanceTo(rayTraceResult.hitVec);
                if (newHitDistance < hitDistance) {
                    pointedEntity = entity;
                    hitDistance = newHitDistance;
                }
            }
        }
        return pointedEntity;
    }

    public static boolean spawnEntity(World world, BlockPos spawnPos, Class<? extends Entity> clazz) {
        try {
            Constructor<? extends Entity> constructor = clazz.getConstructor(World.class);
            Entity entity = constructor.newInstance(world);
            entity.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0.0F);
            world.spawnEntity(entity);
            return true;
        }
        catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T extends Entity> T getEntityByUUID(World world, Class<T> clazz, AxisAlignedBB alignedBB, UUID uuid) {
        for (T entity : world.getEntitiesWithinAABB(clazz, alignedBB))
            if (uuid.equals(entity.getUniqueID()))
                return entity;
        return null;
    }

    public static Vec3d getMotionVector(Entity entity) {
        return new Vec3d(entity.motionX, entity.motionY, entity.motionZ);
    }

    public static BlockPos getPreviousPosition(Entity entity) {
        return new BlockPos(entity.prevPosX, entity.prevPosY, entity.prevPosZ);
    }
}
