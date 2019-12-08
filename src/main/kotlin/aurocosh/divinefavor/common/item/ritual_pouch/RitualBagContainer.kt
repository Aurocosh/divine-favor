package aurocosh.divinefavor.common.item.ritual_pouch

import aurocosh.divinefavor.common.item.base.GenericContainer
import aurocosh.divinefavor.common.lib.extensions.cap
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
import net.minecraftforge.items.SlotItemHandler

class RitualBagContainer(player: EntityPlayer, stack: ItemStack) : GenericContainer(ItemRitualPouch.InventorySize) {
    private val blocked: Int

    init {
        val itemHandler = stack.cap(ITEM_HANDLER_CAPABILITY)

        var x = 62
        var y = 18

        // Add our own slots
        var slotIndex = 0
        for (i in 0..2) {
            addSlotToContainer(SlotItemHandler(itemHandler, slotIndex++, x, y))
            x += 18
        }

        x = 80
        y = 36
        addSlotToContainer(SlotItemHandler(itemHandler, slotIndex++, x, y))

        x = 62
        y = 54
        for (i in 0..2) {
            addSlotToContainer(SlotItemHandler(itemHandler, slotIndex++, x, y))
            x += 18
        }

        generateInventorySlots(player.inventory, 8, 84)
        generateHotbarSlots(player.inventory, 8, 142)

        blocked = inventorySlots.size - 1 - (8 - player.inventory.currentItem)
    }

    override fun slotClick(slot: Int, button: Int, flag: ClickType, player: EntityPlayer): ItemStack {
        return if (slot == blocked) ItemStack.EMPTY else super.slotClick(slot, button, flag, player)
    }
}