package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.item.gems.ItemCallingStone
import aurocosh.divinefavor.common.item.gems.ItemMarkedGlass
import aurocosh.divinefavor.common.lib.enums.InventoryIndexes
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

object UtilSpirit {
    fun convertMarksToInvites(livingBase: EntityLivingBase, spirit: ModSpirit, callingStone: ItemCallingStone) {
        if (livingBase !is EntityPlayer)
            return

        val minSlot = InventoryIndexes.MinSlotIndex.value
        val maxSlot = InventoryIndexes.MaxSlotIndex.value
        val slotRange = minSlot..maxSlot
        val inventory = livingBase.inventory
        for (i in slotRange) {
            val stack = inventory.getStackInSlot(i)
            val item = stack.item
            if (item is ItemMarkedGlass && item.spirit == spirit)
                inventory.setInventorySlotContents(i, ItemStack(callingStone))
        }
    }
}
