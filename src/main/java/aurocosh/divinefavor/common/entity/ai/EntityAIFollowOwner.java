package aurocosh.divinefavor.common.entity.ai;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.base.MinionMode;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class EntityAIFollowOwner<T extends EntityLiving & IMinion> extends EntityAIBase {
    private static final int TELEPORT_ATTEMPTS = 4;
    private static final int TELEPORT_RADIUS = 4;

    private final T minion;
    private final double followSpeed;
    private final PathNavigate petPathfinder;
    private final boolean teleportIfTooFar;
    @Nonnull
    private BooleanSupplier shouldFollow;

    private EntityLivingBase owner;
    private World world;
    private float maxDist;
    private float minDist;
    private float oldWaterCost;
    private int timeToRecalcPath;

    public EntityAIFollowOwner(T minion, double followSpeed, float minDist, float maxDist, boolean teleportIfTooFar, @Nonnull final BooleanSupplier shouldFollow) {
        this.minion = minion;
        world = minion.world;
        petPathfinder = minion.getNavigator();
        this.followSpeed = followSpeed;
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.teleportIfTooFar = teleportIfTooFar;
        this.shouldFollow = shouldFollow;
        setMutexBits(3);

        if (!(minion.getNavigator() instanceof PathNavigateGround))
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        EntityPlayer livingBase = minion.getMinionData().getOwner();
        if (livingBase == null)
            return false;
        else if (livingBase.isSpectator())
            return false;
        else if (minion.getDistanceSq(livingBase) < minDist * minDist)
            return false;
        else if (!shouldFollow.getAsBoolean())
            return false;
        owner = livingBase;
        return true;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !petPathfinder.noPath() && minion.getDistanceSq(owner) > maxDist * maxDist && shouldFollow.getAsBoolean();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        timeToRecalcPath = 0;
        oldWaterCost = minion.getPathPriority(PathNodeType.WATER);
        minion.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        owner = null;
        petPathfinder.clearPath();
        minion.setPathPriority(PathNodeType.WATER, oldWaterCost);
    }

    private boolean isEmptyBlock(BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getMaterial() == Material.AIR || !state.isFullCube();
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        minion.getLookHelper().setLookPositionWithEntity(owner, 10.0F, minion.getVerticalFaceSpeed());
        if (minion.getMinionData().getMode() == MinionMode.Wait)
            return;

        if (--timeToRecalcPath <= 0) {
            timeToRecalcPath = 10;
            if (!petPathfinder.tryMoveToEntityLiving(owner, followSpeed) && teleportIfTooFar)
                tryToTelepotToOwner();
        }
    }

    public void tryToTelepotToOwner() {
        if (minion.getLeashed())
            return;
        if (!(minion.getDistanceSq(owner) >= 144.0D))
            return;

        boolean teleported = false;
        int attempts = TELEPORT_ATTEMPTS;
        while (!teleported && attempts-- > 0) {
            BlockPos pos = UtilCoordinates.INSTANCE.getRandomNeighbour(owner.getPosition(), TELEPORT_RADIUS, 0, TELEPORT_RADIUS);
            teleported = safeTeleport(pos);
            if (!teleported) {
                pos = UtilCoordinates.INSTANCE.findPlaceToStand(pos, minion.world, TELEPORT_RADIUS);
                teleported = safeTeleport(pos);
            }
        }
        petPathfinder.clearPath();
    }

    private boolean safeTeleport(BlockPos pos) {
        if (!isEmptyBlock(pos))
            return false;
        UtilEntity.INSTANCE.teleport(minion, pos);
        return true;
    }
}
