package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.particles.ModParticles;
import aurocosh.divinefavor.common.particles.particles.ImmobileParticle;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.EnumSet;

public class ArrowTalismanTracerArrow extends ItemArrowTalisman {
    public ArrowTalismanTracerArrow(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnParticles(EntitySpellArrow spellArrow) {
        if (spellArrow.isInGround())
            return;

        for (int i = 0; i < ConfigArrow.tracerArrow.particleDensity; i++) {
            Vec3d position = spellArrow.getPositionEyes(UtilRandom.random.nextFloat());
            ModParticles.normal.createParticle(() -> new ImmobileParticle(spellArrow.world, position, spellArrow.getColor(), ConfigArrow.tracerArrow.despawnInterval.random()));
        }
    }
}
