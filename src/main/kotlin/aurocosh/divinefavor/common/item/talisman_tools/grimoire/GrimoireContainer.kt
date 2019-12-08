package aurocosh.divinefavor.common.item.talisman_tools.grimoire

import aurocosh.divinefavor.common.item.base.GenericContainer
import aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability.GrimoireDataHandler
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.network.message.sever.syncing.MessageSyncTalismanContainerSlot
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand

class GrimoireContainer(player: EntityPlayer, stack: ItemStack, private val hand: EnumHand) : GenericContainer(ItemGrimoire.SLOT_COUNT) {
    private val grimoireHandler = stack.cap(GrimoireDataHandler.CAPABILITY_GRIMOIRE)
    private val blocked: Int

    init {
        val x = 8
        val y = 18
        val slotIndex = 0
        // Add our own slots
        generateCustomSlotsGrid(grimoireHandler.getStackHandler(), x, y, 3, 9, slotIndex)

        generateInventorySlots(player.inventory, 8, 84)
        generateHotbarSlots(player.inventory, 8, 142)

        blocked = inventorySlots.size - 1 - (8 - player.inventory.currentItem)
    }

    override fun slotClick(slot: Int, button: Int, flag: ClickType, player: EntityPlayer): ItemStack {
        assert(player.world.isRemote)

        if (slot == blocked)
            return ItemStack.EMPTY
        if (flag == ClickType.CLONE) {
            grimoireHandler.selectedSlotIndex = slot
            player.closeScreen()
            val playerSlot = if (hand == EnumHand.OFF_HAND) 40 else player.inventory.currentItem
            MessageSyncTalismanContainerSlot(playerSlot, slot).send()
            return ItemStack.EMPTY
        }

        return super.slotClick(slot, button, flag, player)
    }
}