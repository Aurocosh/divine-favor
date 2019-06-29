package aurocosh.divinefavor.common.item.memory_pouch

import aurocosh.divinefavor.common.item.base.GenericContainer
import aurocosh.divinefavor.common.item.memory_pouch.capability.IMemoryPouch
import aurocosh.divinefavor.common.network.message.sever.talisman.MessageSyncTalismanContainerSlot
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand

class MemoryPouchTemplateContainer(player: EntityPlayer, private val pouch: IMemoryPouch, private val hand: EnumHand) : GenericContainer(ItemMemoryPouch.SLOT_COUNT) {
    init {
        generateCustomSlotsGrid(pouch.getStackHandler(), 8, 119, 3, 9, 0)
    }

    override fun slotClick(slot: Int, button: Int, flag: ClickType, player: EntityPlayer): ItemStack {
        if (false)
            return ItemStack.EMPTY
        if (flag == ClickType.CLONE) {
            pouch.selectedSlotIndex = slot
            player.closeScreen()
            val playerSlot = if (hand == EnumHand.OFF_HAND) 40 else player.inventory.currentItem
            MessageSyncTalismanContainerSlot(playerSlot, slot).send()
            return ItemStack.EMPTY
        }

        return super.slotClick(slot, button, flag, player)
    }
}