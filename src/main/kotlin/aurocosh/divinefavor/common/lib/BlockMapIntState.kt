package aurocosh.divinefavor.common.lib

import aurocosh.divinefavor.common.block_templates.MetaItem
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
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
        if (findStackSlot(metaItem) !== blockState) {
            intStackMap[blockState] = metaItem
        }
    }

    fun findSlot(mapState: IBlockState): Short {
        for ((key, value) in intStateMap) {
            if (value === mapState) {
                return key
            }
        }
        return -1
    }

    private fun findStackSlot(metaItem: MetaItem): IBlockState? {
        for ((key, value) in intStackMap) {
            if (value.item === metaItem.item && value.meta == metaItem.meta) {
                return key
            }
        }
        return null
    }

    fun getStateFromSlot(slot: Short?): IBlockState {
        return intStateMap.get(slot) ?: Blocks.AIR.defaultState
    }

    companion object {

        fun blockStateToUniqueItem(state: IBlockState, player: EntityPlayer, pos: BlockPos): MetaItem {
            var itemStack: ItemStack
            //if (state.getBlock().canSilkHarvest(player.world, pos, state, player)) {
            //    itemStack = InventoryManipulation.getSilkTouchDrop(state);
            //} else {
            //}
            try {
                itemStack = state.block.getPickBlock(state, null, player.world, pos, player)
            } catch (e: Exception) {
                itemStack = UtilBlock.getSilkTouchDrop(state)
            }

            if (itemStack.isEmpty) {
                itemStack = UtilBlock.getSilkTouchDrop(state)
            }
            return if (!itemStack.isEmpty) {
                MetaItem(itemStack.item, itemStack.metadata)
            } else MetaItem(Items.AIR, 0)
//throw new IllegalArgumentException("A UniqueItem could net be retrieved for the the follwing state (at position " + pos + "): " + state);
        }
    }
}
