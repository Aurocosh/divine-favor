package aurocosh.divinefavor.common.lib.wrapper;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class BlockPredicate extends PredicateWrapper<BlockPos, Block> {
    public BlockPredicate(World world, Predicate<Block> predicate) {
        super(blockPos -> world.getBlockState(blockPos).getBlock(), predicate);
    }

    public BlockPredicate(World world, Block block) {
        super(blockPos -> world.getBlockState(blockPos).getBlock(), value -> value == block);
    }
}
