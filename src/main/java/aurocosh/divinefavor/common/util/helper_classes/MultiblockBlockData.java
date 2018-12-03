package aurocosh.divinefavor.common.util.helper_classes;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class MultiblockBlockData {
    public final BlockPos pos;
    public final Block block;

    public MultiblockBlockData(BlockPos pos, Block block) {
        this.pos = pos;
        this.block = block;
    }
}
