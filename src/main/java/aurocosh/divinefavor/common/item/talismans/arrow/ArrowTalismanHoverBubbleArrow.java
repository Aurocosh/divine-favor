package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.particles.ModParticles;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.EnumSet;

public class ArrowTalismanHoverBubbleArrow extends ArrowTalismanClimbableArrow {
    public ArrowTalismanHoverBubbleArrow(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType, float climbingSpeed, float climbingDistance, int despawnDelay) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType, climbingSpeed, climbingDistance, despawnDelay);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnParticles(EntitySpellArrow spellArrow) {
        for (int i = 0; i < 10; ++i) {
            Vec3d pointOnSphereSurface = UtilRandom.nextDirection().scale(climbingDistance);
            Vec3d pointInWorld = pointOnSphereSurface.add(spellArrow.getPositionVector());
            ModParticles.immobile.createParticle(spellArrow.world, pointInWorld, spellArrow.getColor(), 40, 50);
        }
    }
}
