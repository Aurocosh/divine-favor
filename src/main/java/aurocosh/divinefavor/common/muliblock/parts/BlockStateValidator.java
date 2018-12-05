package aurocosh.divinefavor.common.muliblock.parts;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockStateValidator extends StateValidator {
    public final Block block;

    public BlockStateValidator(Block block) {
        this.block = block;
    }

    @Override
    public boolean isValid(IBlockState state) {
        return state.getBlock() == block;
    }
}
