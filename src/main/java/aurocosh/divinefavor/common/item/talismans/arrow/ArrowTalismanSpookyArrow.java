package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpookyArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.EnumSet;

public class ArrowTalismanSpookyArrow extends ItemArrowTalisman {
    public ArrowTalismanSpookyArrow(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected EntitySpellArrow getArrow(@Nonnull World world, EntityLivingBase shooter) {
        return new EntitySpookyArrow(world, shooter);
    }
}
