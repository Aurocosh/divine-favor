package aurocosh.divinefavor.common.muliblock.validators

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.util.ResourceLocation

class BlockStateValidator(val name: ResourceLocation) : StateValidator() {

    override fun isValid(blockState: IBlockState): Boolean {
        return blockState.block.registryName == name
    }

    override fun getPatchouliMatcher(): Any {
        return Block.getBlockFromName(name.toString()) ?: Blocks.AIR
    }
}
