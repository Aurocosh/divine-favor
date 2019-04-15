package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.entity.rope.IClimbable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityClimbingArrow extends EntitySpellArrow implements IClimbable {
    public EntityClimbingArrow(World worldIn) {
        super(worldIn);
        pickupStatus = PickupStatus.DISALLOWED;
    }

    public EntityClimbingArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        pickupStatus = PickupStatus.DISALLOWED;

    }

    public EntityClimbingArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        pickupStatus = PickupStatus.DISALLOWED;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
        // Players not supposed to pick up this arrow
    }
}