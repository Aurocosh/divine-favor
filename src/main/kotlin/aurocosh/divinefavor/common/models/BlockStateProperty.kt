package aurocosh.divinefavor.common.models

import net.minecraft.block.state.IBlockState
import net.minecraftforge.common.property.IUnlistedProperty

class BlockStateProperty(private val name: String) : IUnlistedProperty<IBlockState> {
    override fun getName() = name
    override fun isValid(value: IBlockState) = true
    override fun getType() = IBlockState::class.java
    override fun valueToString(value: IBlockState) = value.toString()
}
