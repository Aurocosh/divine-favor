package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.constants.BlockPosConstants;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.lib.wrapper.BlockPredicate;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class ArrowTalismanIceBreaker extends ItemArrowTalisman {
    private static final int limit = 600;

    public ArrowTalismanIceBreaker(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        World world = spellArrow.world;

        BlockPredicate predicate = new BlockPredicate(world, UtilBlock::isIce);
        List<BlockPos> posList = UtilCoordinates.INSTANCE.floodFill(Collections.singletonList(blockPos), BlockPosConstants.DIRECT_AND_DIAGONAL, predicate::test, limit);

        for (BlockPos pos : posList)
            UtilBlock.removeBlock((EntityPlayer) shooter, world, ItemStack.EMPTY, pos, true, false, false);
        return true;
    }
}
