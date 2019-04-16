package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.particles.types.ModParticleTypes;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumSet;

public class ArrowTalismanTracerArrow extends ItemArrowTalisman {
    public ArrowTalismanTracerArrow(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnParticles(EntitySpellArrow arrow) {
        if(arrow.isInGround())
            return;

        for (int i = 0; i < 3; i++) {
            Vec3d position = arrow.getPositionEyes(UtilRandom.random.nextFloat());
            DivineFavor.proxy.createParticle(ModParticleTypes.particleStatic, arrow.world, position.x, position.y, position.z, 1200, 2400, 0);
        }
    }
}
