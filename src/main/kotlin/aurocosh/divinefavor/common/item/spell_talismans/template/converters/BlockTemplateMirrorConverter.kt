package aurocosh.divinefavor.common.item.spell_talismans.template.converters

import aurocosh.divinefavor.common.block_templates.BlockTemplate
import aurocosh.divinefavor.common.lib.FaceDiagonalOrientation
import aurocosh.divinefavor.common.util.UtilBlockPos
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumFacing
import net.minecraft.util.Mirror
import net.minecraft.util.math.BlockPos

class BlockTemplateMirrorConverter(private val axis: EnumFacing.Axis) : BlockTemplateConverter() {
    override fun convertOrientation(blockTemplate: BlockTemplate): FaceDiagonalOrientation {
        return blockTemplate.orientation.mirror(axis)
    }

    override fun rotateState(state: IBlockState): IBlockState {
        return when (axis) {
            EnumFacing.Axis.Y -> state
            EnumFacing.Axis.X -> state.withMirror(Mirror.FRONT_BACK)
            else -> state.withMirror(Mirror.LEFT_RIGHT)
        }
    }

    override fun rotatedOffsets(blockTemplate: BlockTemplate): List<BlockPos> {
        val offsets = blockTemplate.posIntArray.map(UtilBlockPos::intToBlockPos)
        val maxIndexes = blockTemplate.boundingBox.sizeVector.subtract(BlockPos(1, 1, 1))
        val flipper = getFlipper(maxIndexes)
        return offsets.map(flipper)
    }

    private fun getFlipper(maxIndexes: BlockPos): (BlockPos) -> BlockPos {
        return when (axis) {
            EnumFacing.Axis.X -> { pos -> BlockPos(maxIndexes.x - pos.x, pos.y, pos.z) }
            EnumFacing.Axis.Y -> { pos -> BlockPos(pos.x, maxIndexes.y - pos.y, pos.z) }
            EnumFacing.Axis.Z -> { pos -> BlockPos(pos.x, pos.y, maxIndexes.z - pos.z) }
        }
    }
}
