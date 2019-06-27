package aurocosh.divinefavor.common.item.spell_talismans.copy

import aurocosh.divinefavor.common.lib.BlockMapIntState
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.util.TemplateBlockState
import aurocosh.divinefavor.common.util.UtilBlockPos
import net.minecraft.util.math.BlockPos
import java.util.*
import kotlin.collections.ArrayList

class BlockTemplate(val uuid: UUID, val blockMapIntState: BlockMapIntState, val posIntArray: IntArray, val stateIntArray: IntArray, val boundingBox: CuboidBoundingBox, val dimension: Int, val creator: String) {

    fun getBlockMapList(startBlock: BlockPos): List<TemplateBlockState> {
        val blockMap = ArrayList<TemplateBlockState>()
        for (i in posIntArray.indices) {
            val serializedOffset = posIntArray[i]
            val offset = UtilBlockPos.intToBlockPos(serializedOffset)
            val blockPos = startBlock.add(offset)
            val intState = stateIntArray[i].toShort()
            blockMap.add(TemplateBlockState(blockPos, offset, blockMapIntState.getStateFromSlot(intState)))
        }
        return blockMap
    }
}