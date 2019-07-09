package aurocosh.divinefavor.common.block_templates

import aurocosh.divinefavor.common.lib.BlockMapIntState
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos

class AbsoluteBlockState(val pos: BlockPos, offset: BlockPos, state: IBlockState) : RelativeBlockState(offset, state)

class SerializedBlockState(val offset: BlockPos, val stateId: Short) {
    fun toRelative(blockMapIntState: BlockMapIntState): RelativeBlockState {
        val state = blockMapIntState.getStateFromSlot(stateId)
        return RelativeBlockState(offset, state)
    }
}

open class RelativeBlockState(val offset: BlockPos, val state: IBlockState) {
    fun toAbsolute(origin: BlockPos): AbsoluteBlockState {
        val pos = origin.add(offset)
        return AbsoluteBlockState(pos, offset, state)
    }
}

