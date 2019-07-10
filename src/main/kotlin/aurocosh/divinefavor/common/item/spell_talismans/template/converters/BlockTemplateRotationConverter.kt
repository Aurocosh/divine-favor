package aurocosh.divinefavor.common.item.spell_talismans.template.converters

import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.lib.FaceDiagonalOrientation
import aurocosh.divinefavor.common.lib.RotationDirection
import aurocosh.divinefavor.common.util.UtilBlockPos
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumFacing
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos

class BlockTemplateRotationConverter(private val axis: EnumFacing.Axis, private val direction: RotationDirection) : BlockTemplateConverter() {
    override fun convertOrientation(blockTemplate: BlockTemplate): FaceDiagonalOrientation {
        return blockTemplate.orientation.rotate(axis, direction, 1)
    }

    override fun rotateState(state: IBlockState): IBlockState {
        if (axis != EnumFacing.Axis.Y)
            return state
        return if (direction == RotationDirection.Clockwise)
            state.withRotation(Rotation.CLOCKWISE_90)
        else
            state.withRotation(Rotation.COUNTERCLOCKWISE_90)
    }

    override fun rotatedOffsets(blockTemplate: BlockTemplate): List<BlockPos> {
        val offsets = blockTemplate.posIntArray.map(UtilBlockPos::intToBlockPos)
        val maxIndexes = blockTemplate.boundingBox.sizeVector.subtract(BlockPos(1, 1, 1))
        val rotator = getRotator(maxIndexes)
        return offsets.map(rotator)
    }

    private fun getRotator(maxIndexes: BlockPos): (BlockPos) -> BlockPos {
        return if (direction == RotationDirection.Clockwise) {
            when (axis) {
                EnumFacing.Axis.X -> { pos -> BlockPos(pos.x, maxIndexes.z - pos.z, pos.y) }
                EnumFacing.Axis.Y -> { pos -> BlockPos(maxIndexes.z - pos.z, pos.y, pos.x) }
                EnumFacing.Axis.Z -> { pos -> BlockPos(maxIndexes.y - pos.y, pos.x, pos.z) }
            }
        } else {
            when (axis) {
                EnumFacing.Axis.X -> { pos -> BlockPos(pos.x, pos.z, maxIndexes.y - pos.y) }
                EnumFacing.Axis.Y -> { pos -> BlockPos(pos.z, pos.y, maxIndexes.x - pos.x) }
                EnumFacing.Axis.Z -> { pos -> BlockPos(pos.y, maxIndexes.x - pos.x, pos.z) }
            }
        }
    }
}
