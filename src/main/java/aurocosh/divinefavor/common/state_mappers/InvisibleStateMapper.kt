package aurocosh.divinefavor.common.state_mappers


import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.block.statemap.IStateMapper

class InvisibleStateMapper : IStateMapper {
    override fun putStateModelLocations(blockIn: Block): Map<IBlockState, ModelResourceLocation> {
        return emptyMap()
    }
}
