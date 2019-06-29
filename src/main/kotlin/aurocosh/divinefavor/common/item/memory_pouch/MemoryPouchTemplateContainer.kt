package aurocosh.divinefavor.common.item.memory_pouch

import aurocosh.divinefavor.common.item.base.GenericContainer
import aurocosh.divinefavor.common.item.memory_drop.ItemMemoryDrop
import aurocosh.divinefavor.common.item.memory_pouch.capability.IMemoryPouch
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.network.message.sever.syncing.MessageSyncMemoryPouchSlot
import aurocosh.divinefavor.common.network.message.sever.syncing.MessageSyncTemplateServer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand

class MemoryPouchTemplateContainer(player: EntityPlayer, private val pouch: IMemoryPouch, private val hand: EnumHand) : GenericContainer(ItemMemoryPouch.SLOT_COUNT) {
    init {
        generateCustomSlotsGrid(pouch.getStackHandler(), 8, 119, 3, 9, 0)
    }

    override fun slotClick(slot: Int, button: Int, flag: ClickType, player: EntityPlayer): ItemStack {
        if (button == 2) {
            selectSlot(slot, player)
            player.closeScreen()
            return ItemStack.EMPTY
        } else if (button == 1 || button == 0) {
            selectSlot(slot, player)
            return ItemStack.EMPTY
        }

        return super.slotClick(slot, button, flag, player)
    }

    private fun selectSlot(slot: Int, player: EntityPlayer) {
        pouch.selectedSlotIndex = slot
        val stack = pouch.getSelectedStack()
        val uuid = stack.get(ItemMemoryDrop.uuid)

        player.divinePlayerData.templateData.currentTemplate = uuid
        MessageSyncTemplateServer(uuid).send()

        val playerSlot = if (hand == EnumHand.OFF_HAND) 40 else player.inventory.currentItem
        MessageSyncMemoryPouchSlot(playerSlot, slot).send()
    }
}