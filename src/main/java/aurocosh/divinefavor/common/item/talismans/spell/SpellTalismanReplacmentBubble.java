package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.entries.talismans.spell.ReplacmentBubble;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.lib.wrapper.BlockPredicate;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class SpellTalismanReplacmentBubble extends ItemSpellTalisman {
    private final int radius;
    private final Block blockInternal;
    private final Block blockExternal;
    private final Predicate<Block> predicate;

    public SpellTalismanReplacmentBubble(String name, ModSpirit spirit, ReplacmentBubble config, Block blockInternal, Block blockExternal, Predicate<Block> predicate) {
        super(name, spirit, config.favorCost, SpellOptions.ALL_CAST);
        radius = config.radius;
        this.blockInternal = blockInternal;
        this.blockExternal = blockExternal;
        this.predicate = predicate;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        int radiusInternal = radius - 1;
        int radiusInternalSq = radiusInternal * radiusInternal;

        World world = context.world;
        List<BlockPos> posList = UtilCoordinates.getBlocksInSphere(context.pos, radius);
        List<BlockPos> internalPosList = UtilList.split(posList, pos -> pos.distanceSq(context.pos) < radiusInternalSq);

        BlockPredicate blockPredicate = new BlockPredicate(world, predicate);
        posList = UtilList.select(posList, blockPredicate);
        internalPosList = UtilList.select(internalPosList, blockPredicate);

        IBlockState state = blockExternal.getDefaultState();
        for (BlockPos pos : posList)
            UtilBlock.replaceBlock(context.player, world, pos, state);

        state = blockInternal.getDefaultState();
        for (BlockPos pos : internalPosList)
            UtilBlock.replaceBlock(context.player, world, pos, state);
    }
}
