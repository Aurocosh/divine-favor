package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.constants.BlockPosConstants;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.lib.wrapper.BlockPredicate;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class SpellTalismanCarving extends ItemSpellTalisman {
    private final Block block;
    private final Predicate<Block> predicate;

    public SpellTalismanCarving(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options, Block block, Predicate<Block> predicate) {
        super(name, spirit, favorCost, options);
        this.block = block;
        this.predicate = predicate;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;

        if (world.getBlockState(context.pos).getBlock() != block)
            return;
        IBlockState state = Blocks.AIR.getDefaultState();
        if (!UtilBlock.replaceBlock(context.player, world, context.pos, state))
            return;

        List<BlockPos> neighbours = UtilList.process(BlockPosConstants.DIRECT_NEIGHBOURS, pos -> pos.add(context.pos));
        neighbours = UtilList.select(neighbours, new BlockPredicate(world, predicate));

        state = block.getDefaultState();
        for (BlockPos pos : neighbours)
            UtilBlock.replaceBlock(context.player, world, pos, state);

    }
}
