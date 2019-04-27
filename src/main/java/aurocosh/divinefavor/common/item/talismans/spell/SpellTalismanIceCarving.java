package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.constants.BlockPosConstants;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.lib.wrapper.BlockPredicate;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class SpellTalismanIceCarving extends ItemSpellTalisman {
    public SpellTalismanIceCarving(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;

        if (!UtilBlock.isIce(world.getBlockState(context.pos).getBlock()))
            return;
        IBlockState state = Blocks.AIR.getDefaultState();
        UtilBlock.replaceBlock(context.player, world, context.pos, state);

        List<BlockPos> neighbours = UtilList.process(BlockPosConstants.DIRECT_NEIGHBOURS, pos -> pos.add(context.pos));
        neighbours = UtilList.select(neighbours, new BlockPredicate(world, UtilBlock::isWater));

        state = Blocks.ICE.getDefaultState();
        for (BlockPos pos : neighbours)
            UtilBlock.replaceBlock(context.player, world, pos, state);

    }
}
