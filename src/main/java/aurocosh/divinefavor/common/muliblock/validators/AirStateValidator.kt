package aurocosh.divinefavor.common.muliblock.validators

import com.google.gson.annotations.Expose
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import vazkii.patchouli.common.multiblock.StateMatcher

class AirStateValidator : StateValidator() {
    @Expose
    internal var test: Int = 0

    override fun isValid(blockState: IBlockState): Boolean {
        return blockState.block === Blocks.AIR
    }

    override fun getPatchouliMatcher(): Any {
        return StateMatcher.AIR
    }
}
