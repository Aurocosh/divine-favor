package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.List;

public class ArrowTalismanIncendiaryArrow extends ItemArrowTalisman {
    public ArrowTalismanIncendiaryArrow(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow, BlockPos blockPos, EnumFacing sideHit) {
        List<BlockPos> posList = UtilCoordinates.getBlocksInSphere(arrow.getPosition(), ConfigArrow.incendiaryArrow.radius);
        posList.forEach(pos -> UtilBlock.ignite(arrow.world, pos));
    }
}
