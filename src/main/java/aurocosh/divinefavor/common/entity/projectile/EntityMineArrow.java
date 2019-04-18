package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class EntityMineArrow extends EntitySpellArrow {
    public EntityMineArrow(World worldIn) {
        super(worldIn);
    }

    public EntityMineArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    protected void entityInit() {
        super.entityInit();
        setDespawnDelay(ConfigArrow.mineArrow.despawnDelay);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        // Players not supposed to pick up this arrow just by standing nearby
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.isRemote)
            return;
        if (!inGround)
            return;

        List<EntityLivingBase> livingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPosition()).grow(ConfigArrow.mineArrow.radius));
        if (livingBases.isEmpty())
            return;

        boolean damageTerrain = ConfigArrow.mineArrow.damageTerrain && !isInWater();
        BlockPos arrowPosition = getPosition();
        world.newExplosion(this, arrowPosition.getX(), arrowPosition.getY(), arrowPosition.getZ(), ConfigArrow.mineArrow.explosionPower, ConfigArrow.mineArrow.causeFire, damageTerrain);
        setDead();
    }
}