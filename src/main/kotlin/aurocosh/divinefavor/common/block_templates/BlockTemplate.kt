package aurocosh.divinefavor.common.block_templates

import aurocosh.divinefavor.common.lib.BlockMapIntState
import aurocosh.divinefavor.common.lib.CornerOrientation
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.util.UtilBlockPos
import net.minecraft.util.math.BlockPos
import java.util.*
import kotlin.collections.ArrayList

class BlockTemplate(val uuid: UUID, val blockMapIntState: BlockMapIntState, val posIntArray: IntArray, val stateIntArray: IntArray, val boundingBox: CuboidBoundingBox, val dimension: Int, val creator: String, val orientation: CornerOrientation = CornerOrientation.UpSouthEast) {

    fun getAbsoluteBlockMap(startBlock: BlockPos): List<AbsoluteBlockState> {
        return getRelativeBlockMap().map { it.toAbsolute(startBlock) }
    }

    fun getRelativeBlockMap(): List<RelativeBlockState> {
        val blockMap = ArrayList<RelativeBlockState>()
        for (i in posIntArray.indices) {
            val serializedOffset = posIntArray[i]
            val offset = UtilBlockPos.intToBlockPos(serializedOffset)
            val intState = stateIntArray[i].toShort()
            val state = blockMapIntState.getStateFromSlot(intState)
            blockMap.add(RelativeBlockState(offset, state))
        }
        return blockMap
    }

}