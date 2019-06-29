package aurocosh.divinefavor.common.item.talisman_tools.spell_bow

import aurocosh.divinefavor.common.item.base.GenericContainer
import aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability.ISpellBowHandler
import aurocosh.divinefavor.common.network.message.sever.syncing.MessageSyncTalismanContainerSlot
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand

class SpellBowContainer(private val player: EntityPlayer, private val spellBowHandler: ISpellBowHandler, private val hand: EnumHand) : GenericContainer(ItemSpellBow.SLOT_COUNT) {
    private val blocked: Int

    init {

        val x = 8
        val y = 18
        val slotIndex = 0
        // Add our own slots
        generateCustomSlotsGrid(spellBowHandler.getStackHandler(), x, y, 3, 9, slotIndex)

        generateInventorySlots(player.inventory, 8, 84)
        generateHotbarSlots(player.inventory, 8, 142)

        blocked = inventorySlots.size - 1 - (8 - player.inventory.currentItem)
    }

    override fun slotClick(slot: Int, button: Int, flag: ClickType, player: EntityPlayer): ItemStack {
        assert(player.world.isRemote)

        if (slot == blocked)
            return ItemStack.EMPTY
        if (flag == ClickType.CLONE) {
            spellBowHandler.selectedSlotIndex = slot
            player.closeScreen()
            val playerSlot = if (hand == EnumHand.OFF_HAND) 40 else player.inventory.currentItem
            MessageSyncTalismanContainerSlot(playerSlot, slot).send()
            return ItemStack.EMPTY
        }

        return super.slotClick(slot, button, flag, player)
    }
}