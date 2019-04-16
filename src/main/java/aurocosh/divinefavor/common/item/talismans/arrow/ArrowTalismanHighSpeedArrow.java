package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class ArrowTalismanHighSpeedArrow extends ItemArrowTalisman {
    private final float extraVelocity;

    public ArrowTalismanHighSpeedArrow(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType, float extraVelocity) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
        this.extraVelocity = extraVelocity;
    }

    @Override
    public void postProcessArrow(EntityArrow entityarrow) {
        Vec3d direction = UtilEntity.getMotionVector(entityarrow);
        UtilEntity.addVelocity(entityarrow, direction, extraVelocity);
    }
}
