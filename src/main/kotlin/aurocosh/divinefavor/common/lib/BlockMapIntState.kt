package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.block_templates.MetaItem
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import java.util.*

class BlockMapIntState(val intStateMap: MutableMap<Short, IBlockState> = HashMap(), val intStackMap: MutableMap<IBlockState, MetaItem> = HashMap()) {

    fun addToMap(mapState: IBlockState) {
        if (findSlot(mapState) == (-1).toShort()) {
            var nextSlot = intStateMap.size.toShort()
            nextSlot++
            intStateMap[nextSlot] = mapState
        }
    }

    fun addToStackMap(metaItem: MetaItem, blockState: IBlockState) {
        if (findStackSlot(metaItem) !== blockState)
            intStackMap[blockState] = metaItem
    }

    fun findSlot(mapState: IBlockState): Short {
        return intStateMap.entries.S.firstOrNull { (_, value) -> value == mapState }?.key ?: -1
    }

    private fun findStackSlot(metaItem: MetaItem): IBlockState? {
        return intStackMap.entries.S.firstOrNull { (_, value) -> value.item === metaItem.item && value.meta == metaItem.meta }?.key
    }

    fun getStateFromSlot(slot: Short?): IBlockState {
        return intStateMap.get(slot) ?: Blocks.AIR.defaultState
    }

    fun generateStackMapFromStateMap(player: EntityPlayer) {
        intStackMap.clear()
        for (value in intStateMap.values)
            intStackMap[value] = blockStateToMetaItem(value, player, BlockPos.ORIGIN)
    }

    companion object {
        fun blockStateToMetaItem(state: IBlockState, player: EntityPlayer, pos: BlockPos): MetaItem {
            var itemStack = try {
                state.block.getPickBlock(state, null, player.world, pos, player)
            } catch (e: Exception) {
                UtilBlock.getSilkTouchDrop(state)
            }

            if (itemStack.isEmpty)
                itemStack = UtilBlock.getSilkTouchDrop(state)
            return if (itemStack.isEmpty) MetaItem() else MetaItem(itemStack.item, itemStack.metadata)
        }
    }
}
