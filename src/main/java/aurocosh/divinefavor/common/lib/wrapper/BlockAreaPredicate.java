package aurocosh.divinefavor.common.lib.wrapper;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class BlockAreaPredicate extends AreaPredicate<Block> {
    public BlockAreaPredicate(World world, Predicate<Block> predicate, List<BlockPos> areaShifts, int matchesRequired) {
        super(blockPos -> world.getBlockState(blockPos).getBlock(), predicate, areaShifts, matchesRequired);
    }

    public BlockAreaPredicate(World world, Block block, List<BlockPos> areaShifts, int matchesRequired) {
        super(blockPos -> world.getBlockState(blockPos).getBlock(), value -> value == block, areaShifts, matchesRequired);
    }
}
