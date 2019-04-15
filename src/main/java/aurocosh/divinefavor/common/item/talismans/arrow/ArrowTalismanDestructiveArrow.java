package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;

public class ArrowTalismanDestructiveArrow extends ItemArrowTalisman {
    private final float maxHardness;

    public ArrowTalismanDestructiveArrow(String name, ModSpirit spirit, int favorCost, int color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType, float maxHardness) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
        this.maxHardness = maxHardness;
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow, BlockPos blockPos, EnumFacing sideHit) {
        World world = arrow.world;
        if (world.isAirBlock(blockPos))
            return;

        IBlockState blockState = world.getBlockState(blockPos);
        float hardness = blockState.getBlockHardness(world, blockPos);
        if (hardness <= maxHardness)
            UtilBlock.removeBlock((EntityPlayer) shooter, world, ItemStack.EMPTY, blockPos, true, false, true);
    }
}
