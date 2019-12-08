package aurocosh.divinefavor.common.item.compressed_item_drop

import aurocosh.divinefavor.common.item.base.GenericContainer
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.util.UtilPlayer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY

class CompressedItemsDropContainer(player: EntityPlayer) : GenericContainer(ItemCompressedItemsDrop.InventorySize) {
    private val stack = ItemStack(ModItems.compressed_items_drop)

    init {
        val itemHandler = stack.cap(ITEM_HANDLER_CAPABILITY)

        val x = 8
        val y = 18
        val slotIndex = 0
        // Add our own slots
        generateCustomSlotsGrid(itemHandler, x, y, 3, 9, slotIndex)

        generateInventorySlots(player.inventory, 8, 84)
        generateHotbarSlots(player.inventory, 8, 142)
    }

    override fun onContainerClosed(player: EntityPlayer) {
        super.onContainerClosed(player)
        UtilPlayer.addStackToInventoryOrDrop(player, stack)
    }
}