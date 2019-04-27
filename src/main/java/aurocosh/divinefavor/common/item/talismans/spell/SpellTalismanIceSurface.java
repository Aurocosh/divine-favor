package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.constants.BlockPosConstants;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.lib.wrapper.BlockAreaPredicate;
import aurocosh.divinefavor.common.lib.wrapper.BlockPredicate;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class SpellTalismanIceSurface extends ItemSpellTalisman {
    public SpellTalismanIceSurface(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;

        Predicate<BlockPos> waterPredicate = new BlockPredicate(world, UtilBlock::isWater);
        Predicate<BlockPos> airPredicate = new BlockAreaPredicate(world, Blocks.AIR, BlockPosConstants.DIRECT_NEIGHBOURS, 1);
        Predicate<BlockPos> predicate = waterPredicate.and(airPredicate);

        List<BlockPos> posList = UtilCoordinates.getBlocksInSphere(context.pos, ConfigSpells.iceSurface.radius);
        posList = UtilList.select(posList, predicate);
        posList = UtilCoordinates.floodFill(posList, BlockPosConstants.DIRECT_NEIGHBOURS, predicate, ConfigSpells.iceSurface.floodLimit);

        IBlockState state = Blocks.ICE.getDefaultState();
        for (BlockPos pos : posList)
            UtilBlock.replaceBlock(context.player, world, pos, state);
    }
}
