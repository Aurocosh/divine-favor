package aurocosh.divinefavor.common.entity.projectile

import aurocosh.divinefavor.common.entity.rope.IClimbable
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.world.World

class EntityClimbingArrow : EntitySpellArrow, IClimbable {

    private var climbingSpeed: Float = 0f
    private var climbingDistanceSq: Float = 0f

    constructor(worldIn: World) : super(worldIn) {}

    constructor(worldIn: World, shooter: EntityLivingBase) : super(worldIn, shooter) {}

    fun setClimbingStats(climbingSpeed: Float, climbingDistanceSq: Float, despawnDelay: Int) {
        this.climbingSpeed = climbingSpeed
        this.climbingDistanceSq = climbingDistanceSq
        setDespawnDelay(despawnDelay)

        dataManager.set(CLIMBING_SPEED, climbingSpeed)
        dataManager.set(CLIMBING_DISTANCE_SQ, climbingDistanceSq)
    }

    override fun entityInit() {
        super.entityInit()
        dataManager.register(CLIMBING_SPEED, 0f)
        dataManager.register(CLIMBING_DISTANCE_SQ, 0f)

        pickupStatus = PickupStatus.DISALLOWED
    }

    override fun notifyDataManagerChange(key: DataParameter<*>) {
        super.notifyDataManagerChange(key)
        if (CLIMBING_SPEED == key)
            climbingSpeed = dataManager.get(CLIMBING_SPEED)
        else if (CLIMBING_DISTANCE_SQ == key)
            climbingDistanceSq = dataManager.get(CLIMBING_DISTANCE_SQ)
    }

    override fun writeEntityToNBT(compound: NBTTagCompound) {
        super.writeEntityToNBT(compound)

        compound.setFloat(TAG_CLIMBING_SPEED, climbingSpeed)
        compound.setFloat(TAG_CLIMBING_DISTANCE_SQ, climbingDistanceSq)
    }

    override fun readEntityFromNBT(compound: NBTTagCompound) {
        super.readEntityFromNBT(compound)

        climbingSpeed = compound.getFloat(TAG_CLIMBING_SPEED)
        climbingDistanceSq = compound.getFloat(TAG_CLIMBING_DISTANCE_SQ)
    }

    override fun getClimbingSpeed(): Float {
        return climbingSpeed
    }

    override fun canClimb(entity: Entity): Boolean {
        return inGround && entity.getDistanceSq(this) <= climbingDistanceSq
    }

    companion object {
        private val CLIMBING_SPEED = EntityDataManager.createKey(EntityClimbingArrow::class.java, DataSerializers.FLOAT)
        private val CLIMBING_DISTANCE_SQ = EntityDataManager.createKey(EntityClimbingArrow::class.java, DataSerializers.FLOAT)

        private val TAG_CLIMBING_SPEED = "ClimbingSpeed"
        private val TAG_CLIMBING_DISTANCE_SQ = "ClimbingDistanceSq"
    }
}