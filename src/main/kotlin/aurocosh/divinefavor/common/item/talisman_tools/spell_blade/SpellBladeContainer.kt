package aurocosh.divinefavor.common.item.talisman_tools.spell_blade

import aurocosh.divinefavor.common.item.base.GenericContainer
import aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability.ISpellBladeHandler
import aurocosh.divinefavor.common.network.message.sever.talisman.MessageSyncTalismanContainerSlot
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand

class SpellBladeContainer(player: EntityPlayer, private val spellBladeHandler: ISpellBladeHandler, private val hand: EnumHand) : GenericContainer(ItemSpellBlade.SLOT_COUNT) {
    private val blocked: Int

    init {
        val x = 8
        val y = 18
        val slotIndex = 0
        // Add our own slots
        generateCustomSlotsGrid(spellBladeHandler.getStackHandler(), x, y, 2, 9, slotIndex)

        generateInventorySlots(player.inventory, 8, 66)
        generateHotbarSlots(player.inventory, 8, 124)

        blocked = inventorySlots.size - 1 - (8 - player.inventory.currentItem)
    }

    override fun slotClick(slot: Int, button: Int, flag: ClickType, player: EntityPlayer): ItemStack {
        if (slot == blocked)
            return ItemStack.EMPTY
        if (flag == ClickType.CLONE) {
            spellBladeHandler.selectedSlotIndex = slot
            player.closeScreen()
            val playerSlot = if (hand == EnumHand.OFF_HAND) 40 else player.inventory.currentItem
            MessageSyncTalismanContainerSlot(playerSlot, slot).send()
            return ItemStack.EMPTY
        }

        return super.slotClick(slot, button, flag, player)
    }
}