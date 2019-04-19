package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.entity.rope.IClimbable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityClimbingArrow extends EntitySpellArrow implements IClimbable {
    private static final DataParameter<Float> CLIMBING_SPEED = EntityDataManager.createKey(EntityClimbingArrow.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> CLIMBING_DISTANCE_SQ = EntityDataManager.createKey(EntityClimbingArrow.class, DataSerializers.FLOAT);

    private static final String TAG_CLIMBING_SPEED = "ClimbingSpeed";
    private static final String TAG_CLIMBING_DISTANCE_SQ = "ClimbingDistanceSq";

    private float climbingSpeed;
    private float climbingDistanceSq;

    public EntityClimbingArrow(World worldIn) {
        super(worldIn);
    }

    public EntityClimbingArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    public void setClimbingStats(float climbingSpeed, float climbingDistanceSq, int despawnDelay) {
        this.climbingSpeed = climbingSpeed;
        this.climbingDistanceSq = climbingDistanceSq;
        setDespawnDelay(despawnDelay);

        dataManager.set(CLIMBING_SPEED, climbingSpeed);
        dataManager.set(CLIMBING_DISTANCE_SQ, climbingDistanceSq);
    }

    protected void entityInit() {
        super.entityInit();
        dataManager.register(CLIMBING_SPEED, 0f);
        dataManager.register(CLIMBING_DISTANCE_SQ, 0f);

        pickupStatus = PickupStatus.DISALLOWED;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (CLIMBING_SPEED.equals(key))
            climbingSpeed = dataManager.get(CLIMBING_SPEED);
        else if (CLIMBING_DISTANCE_SQ.equals(key))
            climbingDistanceSq = dataManager.get(CLIMBING_DISTANCE_SQ);
    }



    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setFloat(TAG_CLIMBING_SPEED, climbingSpeed);
        compound.setFloat(TAG_CLIMBING_DISTANCE_SQ, climbingDistanceSq);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        climbingSpeed = compound.getFloat(TAG_CLIMBING_SPEED);
        climbingDistanceSq = compound.getFloat(TAG_CLIMBING_DISTANCE_SQ);
    }
    
    @Override
    public float getClimbingSpeed() {
        return climbingSpeed;
    }

    @Override
    public boolean canClimb(Entity entityIn) {
        return inGround && entityIn.getDistanceSq(this) <= climbingDistanceSq;
    }
}