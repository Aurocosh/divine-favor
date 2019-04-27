package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class ArrowTalismanIceSphere extends ItemArrowTalisman {
    public ArrowTalismanIceSphere(String name, ModSpirit spirit, int favorCost, Color color, double arrowDamage, EnumSet<ArrowOptions> options, ArrowType arrowType) {
        super(name, spirit, favorCost, color, arrowDamage, options, arrowType);
    }

    @Override
    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        World world = spellArrow.world;

        List<BlockPos> sphereOutline = UtilCoordinates.INSTANCE.getSphereOutline(blockPos, ConfigArrow.iceSphereArrow.internalRadius, ConfigArrow.iceSphereArrow.externalRadius);
        sphereOutline = UtilList.select(sphereOutline, pos -> UtilBlock.isAirOrReplaceable(world, pos));

        IBlockState defaultState = Blocks.ICE.getDefaultState();
        for (BlockPos pos : sphereOutline)
            UtilBlock.replaceBlock((EntityPlayer) shooter, world, pos, defaultState);
        return true;
    }
}
