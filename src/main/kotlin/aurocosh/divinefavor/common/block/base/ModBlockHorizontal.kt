package aurocosh.divinefavor.common.block.base

import net.minecraft.block.BlockHorizontal
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class ModBlockHorizontal(name: String, material: Material, orderIndex: Int) : ModBlock(name, material, orderIndex) {

    override fun getStateForPlacement(worldIn: World?, pos: BlockPos?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState {
        return this.defaultState.withProperty(FACING, placer.horizontalFacing.opposite)
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, FACING)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState.withProperty(FACING, EnumFacing.byIndex(meta and 7))
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(FACING).index
    }

    companion object {
        val FACING: PropertyDirection = BlockHorizontal.FACING
    }
}
