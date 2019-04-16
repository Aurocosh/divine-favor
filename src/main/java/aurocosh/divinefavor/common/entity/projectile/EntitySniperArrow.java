package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.util.UtilNbt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySniperArrow extends EntitySpellArrow {
    private final static String TAG_STARTING_POS = "StartingPos";

    private Vec3d startingPosition;

    public EntitySniperArrow(World worldIn) {
        super(worldIn);
    }

    public EntitySniperArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntitySniperArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    public Vec3d getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(Vec3d startingPosition) {
        this.startingPosition = startingPosition;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        UtilNbt.setVec3d(compound, TAG_STARTING_POS, startingPosition);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        startingPosition = UtilNbt.getVec3d(compound, TAG_STARTING_POS);
    }
}