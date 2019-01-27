package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class ArrowTalismanNetherSwap extends ItemArrowTalisman {
    public ArrowTalismanNetherSwap(String name, ModFavor favor, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, favor, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        BlockPos targetPos = target.getPosition();
        BlockPos shooterPos = shooter.getPosition();
        UtilEntity.teleport(shooter, targetPos);
        UtilEntity.teleport(target, shooterPos);
    }
}
