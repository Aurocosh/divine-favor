package aurocosh.divinefavor.common.muliblock.patchouli

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.util.ResourceLocation
import vazkii.patchouli.api.IStateMatcher
import java.util.function.Predicate

class MultiBlockMatcher(private val names: List<ResourceLocation>) : IStateMatcher {

    override fun getDisplayedState(): IBlockState {
        val location = names[0]
        val block = Block.getBlockFromName(location.toString())
        return block!!.defaultState
    }

    override fun getStatePredicate(): Predicate<IBlockState> {
        return Predicate { this.checkBlockState(it) }
    }

    private fun checkBlockState(iBlockState: IBlockState): Boolean {
        for (name in names)
            if (iBlockState.block.registryName == name)
                return true
        return false
    }
}
