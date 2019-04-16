package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityVacuumArrow extends EntitySpellArrow {
    private static final int RADIUS_SQ = ConfigArrow.vacuumArrow.radius * ConfigArrow.vacuumArrow.radius;

    public EntityVacuumArrow(World worldIn) {
        super(worldIn);
        init();
    }

    public EntityVacuumArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        init();
    }

    public EntityVacuumArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        init();
    }

    private void init() {
        setDespawnDelay(ConfigArrow.vacuumArrow.despawnDelay);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.isRemote)
            return;

        List<EntityLivingBase> livingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPosition()).grow(ConfigArrow.vacuumArrow.radius));
        List<EntityLivingBase> affectedMobs = UtilList.select(livingBases, element -> !(element instanceof EntityPlayer) && element.getDistanceSq(this) <= RADIUS_SQ);

        for (EntityLivingBase affectedMob : affectedMobs) {
            Vec3d direction = affectedMob.getPositionVector().subtract(this.getPositionVector());
            UtilEntity.addVelocity(affectedMob, direction, ConfigArrow.vacuumArrow.attractionPower);
        }
    }
}