package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.entity.projectile.EntityStasisArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.particles.types.ModParticleTypes;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class ArrowTalismanStasisArrow extends ItemArrowTalisman {
    public ArrowTalismanStasisArrow(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected EntitySpellArrow getArrow(@Nonnull World world, EntityLivingBase shooter) {
        return new EntityStasisArrow(world, shooter);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnParticles(EntitySpellArrow arrow) {
        for (int i = 0; i < 10; ++i) {
            Vec3d pointOnSphereSurface = UtilRandom.nextDirection().scale(ConfigArrow.stasisArrow.radius);
            Vec3d pointInWorld = pointOnSphereSurface.add(arrow.getPositionVector());
            DivineFavor.proxy.createParticle(ModParticleTypes.particleStatic, arrow.world, pointInWorld.x, pointInWorld.y, pointInWorld.z, 40, 50, 0);
        }
    }
}
