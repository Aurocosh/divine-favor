package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.entity.projectile.EntityVacuumArrow;
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

public class ArrowTalismanVacuumArrow extends ItemArrowTalisman {
    public ArrowTalismanVacuumArrow(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected EntitySpellArrow getArrow(@Nonnull World world, EntityLivingBase shooter) {
        return new EntityVacuumArrow(world, shooter);
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
}
