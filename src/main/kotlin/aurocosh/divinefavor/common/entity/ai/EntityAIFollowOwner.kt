package aurocosh.divinefavor.common.entity.ai

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.base.MinionMode
import aurocosh.divinefavor.common.util.UtilCoordinates
import aurocosh.divinefavor.common.util.UtilEntity
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.pathfinding.PathNavigate
import net.minecraft.pathfinding.PathNavigateGround
import net.minecraft.pathfinding.PathNodeType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.function.BooleanSupplier

class EntityAIFollowOwner<T>(private val minion: T, private val followSpeed: Double, private val minDist: Float, private val maxDist: Float, private val teleportIfTooFar: Boolean, private val shouldFollow: BooleanSupplier) : EntityAIBase() where T : IMinion, T : EntityLiving {
    private val petPathfinder: PathNavigate

    private var owner: EntityLivingBase? = null
    private val world: World
    private var oldWaterCost: Float = 0.toFloat()
    private var timeToRecalcPath: Int = 0

    init {
        world = minion.world
        petPathfinder = minion.navigator
        mutexBits = 3

        if (minion.navigator !is PathNavigateGround)
            throw IllegalArgumentException("Unsupported mob type for FollowOwnerGoal")
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    override fun shouldExecute(): Boolean {
        val livingBase = minion.minionData.owner
        if (livingBase == null)
            return false
        else if (livingBase.isSpectator)
            return false
        else if (minion.getDistanceSq(livingBase) < minDist * minDist)
            return false
        else if (!shouldFollow.asBoolean)
            return false
        owner = livingBase
        return true
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    override fun shouldContinueExecuting(): Boolean {
        return !petPathfinder.noPath() && minion.getDistanceSq(owner!!) > maxDist * maxDist && shouldFollow.asBoolean
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    override fun startExecuting() {
        timeToRecalcPath = 0
        oldWaterCost = minion.getPathPriority(PathNodeType.WATER)
        minion.setPathPriority(PathNodeType.WATER, 0.0f)
    }

    /**
     * Resets the task
     */
    override fun resetTask() {
        owner = null
        petPathfinder.clearPath()
        minion.setPathPriority(PathNodeType.WATER, oldWaterCost)
    }

    private fun isEmptyBlock(pos: BlockPos?): Boolean {
        val state = world.getBlockState(pos!!)
        return state.material === Material.AIR || !state.isFullCube
    }

    /**
     * Updates the task
     */
    override fun updateTask() {
        minion.lookHelper.setLookPositionWithEntity(owner!!, 10.0f, minion.verticalFaceSpeed.toFloat())
        if (minion.minionData.mode === MinionMode.Wait)
            return

        if (--timeToRecalcPath <= 0) {
            timeToRecalcPath = 10
            if (!petPathfinder.tryMoveToEntityLiving(owner!!, followSpeed) && teleportIfTooFar)
                tryToTelepotToOwner()
        }
    }

    fun tryToTelepotToOwner() {
        if (minion.leashed)
            return
        if (minion.getDistanceSq(owner!!) < 144.0)
            return

        var teleported = false
        var attempts = TELEPORT_ATTEMPTS
        while (!teleported && attempts-- > 0) {
            var pos: BlockPos? = UtilCoordinates.getRandomNeighbour(owner!!.position, TELEPORT_RADIUS, 0, TELEPORT_RADIUS)
            teleported = safeTeleport(pos)
            if (!teleported) {
                pos = UtilCoordinates.findPlaceToStand(pos!!, minion.world, TELEPORT_RADIUS)
                teleported = safeTeleport(pos)
            }
        }
        petPathfinder.clearPath()
    }

    private fun safeTeleport(pos: BlockPos?): Boolean {
        if (!isEmptyBlock(pos))
            return false
        UtilEntity.teleport(minion, pos!!)
        return true
    }

    companion object {
        private val TELEPORT_ATTEMPTS = 4
        private val TELEPORT_RADIUS = 4
    }
}
