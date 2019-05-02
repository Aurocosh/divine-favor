package aurocosh.divinefavor.common.block.base

import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockFaceShape
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

open class ModBlockAir(name: String, material: Material, orderIndex: Int) : ModBlock(name, material, orderIndex) {

    override fun getRenderType(state: IBlockState?): EnumBlockRenderType {
        return EnumBlockRenderType.INVISIBLE
    }

    override fun getCollisionBoundingBox(blockState: IBlockState, worldIn: IBlockAccess, pos: BlockPos): AxisAlignedBB? {
        return NULL_AABB
    }

    override fun isOpaqueCube(state: IBlockState?): Boolean {
        return false
    }

    override fun canCollideCheck(state: IBlockState?, hitIfLiquid: Boolean): Boolean {
        return false
    }

    override fun dropBlockAsItemWithChance(worldIn: World, pos: BlockPos, state: IBlockState, chance: Float, fortune: Int) {}

    override fun isReplaceable(worldIn: IBlockAccess, pos: BlockPos): Boolean {
        return true
    }

    override fun isFullCube(state: IBlockState?): Boolean {
        return false
    }

    override fun getBlockFaceShape(worldIn: IBlockAccess?, state: IBlockState?, pos: BlockPos?, face: EnumFacing?): BlockFaceShape {
        return BlockFaceShape.UNDEFINED
    }

}
