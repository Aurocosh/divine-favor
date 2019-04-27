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

public class SpellTalismanLakeThawing extends ItemSpellTalisman {
    public SpellTalismanLakeThawing(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;

        Predicate<BlockPos> icePredicate = new BlockPredicate(world, UtilBlock::isIce);
        List<BlockPos> neighbours = UtilList.unite(BlockPosConstants.HORIZONTAL_DIRECT, BlockPosConstants.DOWN);
        Predicate<BlockPos> airPredicate = new BlockAreaPredicate(world, block -> block != Blocks.AIR, neighbours, neighbours.size());
        Predicate<BlockPos> predicate = icePredicate.and(airPredicate);

        List<BlockPos> posList = UtilCoordinates.INSTANCE.getBlocksInSphere(context.pos, ConfigSpells.lakeThawing.radius);
        posList = UtilList.select(posList, predicate);
        posList = UtilCoordinates.INSTANCE.floodFill(posList, BlockPosConstants.DIRECT_NEIGHBOURS, predicate::test, ConfigSpells.lakeThawing.floodLimit);

        IBlockState state = Blocks.WATER.getDefaultState();
        for (BlockPos pos : posList)
            UtilBlock.replaceBlock(context.player, world, pos, state);
    }
}
