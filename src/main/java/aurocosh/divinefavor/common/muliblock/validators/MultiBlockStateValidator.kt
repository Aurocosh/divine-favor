package aurocosh.divinefavor.common.muliblock.validators

import aurocosh.divinefavor.common.muliblock.patchouli.MultiBlockMatcher
import net.minecraft.block.state.IBlockState
import net.minecraft.util.ResourceLocation

class MultiBlockStateValidator(val names: List<ResourceLocation>) : StateValidator() {

    override fun isValid(blockState: IBlockState): Boolean {
        val name = blockState.block.registryName ?: return false
        return names.any(name::equals)
    }

    override fun getPatchouliMatcher(): Any {
        return MultiBlockMatcher(names)
    }
}
