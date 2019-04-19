package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.particles.types.ModParticleTypes;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumSet;
import java.util.List;

public class ArrowTalismanVacuumArrow extends ItemArrowTalisman {
    private static final int RADIUS_SQ = ConfigArrow.vacuumArrow.radius * ConfigArrow.vacuumArrow.radius;

    public ArrowTalismanVacuumArrow(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected void init(EntitySpellArrow spellArrow, EntityLivingBase shooter) {
        spellArrow.setDespawnDelay(ConfigArrow.vacuumArrow.despawnDelay);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnParticles(EntitySpellArrow arrow) {
        for (int i = 0; i < 3; ++i) {
            double distance = UtilRandom.nextFloat(4, 9);
            Vec3d direction = UtilRandom.nextDirection();
            Vec3d pointOnSphere = direction.scale(distance);
            Vec3d pointInWorld = pointOnSphere.add(arrow.getPositionVector());
            Vec3d speed = direction.scale(-0.3f);
            DivineFavor.proxy.createParticle(ModParticleTypes.vacuumArrow, arrow.world, pointInWorld.x, pointInWorld.y, pointInWorld.z, speed.x, speed.y, speed.z);
        }
    }

    @Override
    public void onUpdate(EntitySpellArrow spellArrow) {
        if (spellArrow.world.isRemote)
            return;
        if(!spellArrow.isInGround())
            return;

        List<EntityLivingBase> livingBases = spellArrow.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(spellArrow.getPosition()).grow(ConfigArrow.vacuumArrow.radius));
        List<EntityLivingBase> affectedMobs = UtilList.select(livingBases, element -> !(element instanceof EntityPlayer) && element.getDistanceSq(spellArrow) <= RADIUS_SQ);

        for (EntityLivingBase affectedMob : affectedMobs) {
            Vec3d direction = affectedMob.getPositionVector().subtract(spellArrow.getPositionVector());
            UtilEntity.addVelocity(affectedMob, direction, ConfigArrow.vacuumArrow.attractionPower);
        }
    }
}
