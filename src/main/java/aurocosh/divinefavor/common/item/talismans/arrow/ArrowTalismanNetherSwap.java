package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class ArrowTalismanNetherSwap extends ItemArrowTalisman {
    public ArrowTalismanNetherSwap(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, EnumSet.of(ArrowOptions.BreakOnHit, ArrowOptions.RequiresTarget), arrowType);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow, BlockPos blockPos, EnumFacing sideHit) {
        BlockPos targetPos = target.getPosition();
        BlockPos shooterPos = shooter.getPosition();
        UtilEntity.teleport(shooter, targetPos);
        UtilEntity.teleport(target, shooterPos);
    }
}
