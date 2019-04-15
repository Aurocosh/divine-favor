package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.particles.types.ModParticleTypes;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumSet;

public class ArrowTalismanHoverBubbleArrow extends ArrowTalismanClimbableArrow {
    public ArrowTalismanHoverBubbleArrow(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType, float climbingSpeed, float climbingDistance, int despawnDelay) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType, climbingSpeed, climbingDistance, despawnDelay);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnParticles(EntitySpellArrow arrow) {
        for (int i = 0; i < 12; ++i) {
            Vec3d pointOnSphereSurface = UtilRandom.nextDirection().scale(climbingDistance);
            Vec3d pointInWorld = pointOnSphereSurface.add(arrow.getPositionVector());
            DivineFavor.proxy.createParticle(ModParticleTypes.vacuumArrow, arrow.world, pointInWorld.x, pointInWorld.y, pointInWorld.z, 0, 0, 0);
        }
    }
}
