package aurocosh.divinefavor.common.muliblock.validators

import net.minecraft.block.state.IBlockState

abstract class StateValidator {
    abstract fun getPatchouliMatcher(): Any
    abstract fun isValid(blockState: IBlockState): Boolean
}
