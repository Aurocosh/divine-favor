package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.particles.ParticleHandler;
import aurocosh.divinefavor.common.particles.types.ModParticleTypes;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityVacuumArrow extends EntitySpellArrow {
    private static final int RADIUS = 6;
    private static final int RADIUS_SQ = RADIUS * RADIUS;
    private static final float REPULSION_POWER = -0.05f;

    public EntityVacuumArrow(World worldIn) {
        super(worldIn);
    }

    public EntityVacuumArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);

    }

    public EntityVacuumArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }


    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.isRemote) {
            spawnParticles();
        }
        else {
            List<EntityLivingBase> livingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(getPosition()).grow(RADIUS));
            List<EntityLivingBase> affectedMobs = UtilList.select(livingBases, element -> !(element instanceof EntityPlayer) && element.getDistanceSq(this) <= RADIUS_SQ);

            for (EntityLivingBase affectedMob : affectedMobs) {
                Vec3d direction = affectedMob.getPositionVector().subtract(this.getPositionVector());
                UtilEntity.addVelocity(affectedMob, direction, REPULSION_POWER);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        for (int i = 0; i < 3; ++i) {
            double scale = UtilRandom.nextFloat(4, 9);
            Vec3d point = UtilRandom.nextDirection();
            Vec3d point2 = point.scale(scale);
            Vec3d add = point2.add(this.getPositionVector());

            Vec3d point3 = point.scale(0.3f);
            ParticleHandler.createParticle(ModParticleTypes.vacuumArrow, world, add.x, add.y, add.z, -point3.x, -point3.y, -point3.z);
        }
    }
}