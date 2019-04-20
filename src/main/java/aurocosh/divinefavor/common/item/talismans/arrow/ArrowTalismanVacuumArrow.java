package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.particles.ModParticles;
import aurocosh.divinefavor.common.particles.particles.MobileParticle;
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

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class ArrowTalismanVacuumArrow extends ItemArrowTalisman {
    private static final int RADIUS_SQ = ConfigArrow.vacuumArrow.radius * ConfigArrow.vacuumArrow.radius;

    public ArrowTalismanVacuumArrow(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected void init(EntitySpellArrow spellArrow, EntityLivingBase shooter) {
        spellArrow.setDespawnDelay(ConfigArrow.vacuumArrow.despawnDelay);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnParticles(EntitySpellArrow spellArrow) {
        for (int i = 0; i < 3; ++i) {
            double distance = UtilRandom.nextFloat(4, 9);
            Vec3d direction = UtilRandom.nextDirection();
            Vec3d pointOnSphere = direction.scale(distance);
            Vec3d pointInWorld = pointOnSphere.add(spellArrow.getPositionVector());
            Vec3d speed = direction.scale(-0.3f);
            ModParticles.normal.createParticle(pointInWorld, () -> new MobileParticle(spellArrow.world, pointInWorld, speed, spellArrow.getColor()));
        }
    }

    @Override
    public void onUpdate(EntitySpellArrow spellArrow) {
        if (spellArrow.world.isRemote)
            return;
        if (!spellArrow.isInGround())
            return;

        List<EntityLivingBase> livingBases = spellArrow.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(spellArrow.getPosition()).grow(ConfigArrow.vacuumArrow.radius));
        List<EntityLivingBase> affectedMobs = UtilList.select(livingBases, element -> !(element instanceof EntityPlayer) && element.getDistanceSq(spellArrow) <= RADIUS_SQ);

        for (EntityLivingBase affectedMob : affectedMobs) {
            Vec3d direction = affectedMob.getPositionVector().subtract(spellArrow.getPositionVector());
            UtilEntity.addVelocity(affectedMob, direction, ConfigArrow.vacuumArrow.attractionPower);
        }
    }
}
