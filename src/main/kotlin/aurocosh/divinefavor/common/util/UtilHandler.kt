package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.misc.SlotStack
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import java.util.*

object UtilHandler {
    fun getNotEmptyStacksWithSlotIndexes(itemHandler: IItemHandler): List<SlotStack> {
        val stackList = ArrayList<SlotStack>()
        val stackCount = itemHandler.slots

        for (i in 0 until stackCount) {
            val stack = itemHandler.getStackInSlot(i)
            if (!stack.isEmpty)
                stackList.add(SlotStack(i, stack))
        }
        return stackList
    }

    fun getNotEmptyStacks(itemHandler: IItemHandler): List<ItemStack> {
        val stackCount = itemHandler.slots
        val stackList = ArrayList<ItemStack>(stackCount)

        for (i in 0 until stackCount) {
            val stack = itemHandler.getStackInSlot(i)
            if (!stack.isEmpty)
                stackList.add(stack)
        }
        return stackList
    }

    fun getAllStacks(itemHandler: IItemHandler): List<ItemStack> {
        val stackCount = itemHandler.slots
        val stackList = ArrayList<ItemStack>(stackCount)

        for (i in 0 until stackCount)
            stackList.add(itemHandler.getStackInSlot(i))
        return stackList
    }
}
