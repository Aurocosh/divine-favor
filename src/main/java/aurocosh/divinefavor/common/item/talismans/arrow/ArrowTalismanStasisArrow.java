package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.particles.ModParticles;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class ArrowTalismanStasisArrow extends ItemArrowTalisman {
    private static final int RADIUS_SQ = ConfigArrow.stasisArrow.radius * ConfigArrow.stasisArrow.radius;

    public ArrowTalismanStasisArrow(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected void init(EntitySpellArrow spellArrow, EntityLivingBase shooter) {
        spellArrow.setDespawnDelay(ConfigArrow.stasisArrow.despawnDelay);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnParticles(EntitySpellArrow spellArrow) {
        for (int i = 0; i < 10; ++i) {
            Vec3d pointOnSphereSurface = UtilRandom.nextDirection().scale(ConfigArrow.stasisArrow.radius);
            Vec3d pointInWorld = pointOnSphereSurface.add(spellArrow.getPositionVector());
            ModParticles.immobile.createParticle(spellArrow.world, pointInWorld, spellArrow.getColor(), 40, 50);
        }
    }

    @Override
    public boolean onCollideWithPlayer(EntitySpellArrow spellArrow, EntityPlayer entityIn) {
        return false;
    }

    @Override
    public void onUpdate(EntitySpellArrow spellArrow) {
        if (!spellArrow.isInGround())
            return;
        List<Entity> entities = spellArrow.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(spellArrow.getPosition()).grow(ConfigArrow.stasisArrow.radius));
        List<Entity> affectedMobs = UtilList.select(entities, element -> element != spellArrow && !(element instanceof EntityPlayer) && (element instanceof EntityLivingBase || element instanceof EntityArrow) && element.getDistanceSq(spellArrow) <= RADIUS_SQ);
        for (Entity affectedMob : affectedMobs)
            UtilEntity.setVelocity(affectedMob, UtilEntity.getMotionVector(affectedMob), 0.01f);
    }
}
