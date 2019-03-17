package aurocosh.divinefavor.common.muliblock.validators;

import net.minecraft.block.state.IBlockState;

public abstract class StateValidator {
    public abstract boolean isValid(IBlockState blockState);
    public abstract Object getPatchouliMatcher();
}
