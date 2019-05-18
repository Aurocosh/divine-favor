package aurocosh.divinefavor.common.item.contract_binder

import aurocosh.divinefavor.common.item.base.GenericContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler

class ContractBinderContainer(player: EntityPlayer, itemHandler: IItemHandler) : GenericContainer(ItemContractBinder.SIZE) {
    private val blocked: Int

    init {
        generateCustomSlotsGrid(itemHandler, 26, 18, 1, 7, 0)
        generateInventorySlots(player.inventory, 8, 48)
        generateHotbarSlots(player.inventory, 8, 106)

        blocked = inventorySlots.size - 1 - (8 - player.inventory.currentItem)
    }

    override fun slotClick(slot: Int, button: Int, flag: ClickType, player: EntityPlayer): ItemStack {
        return if (slot == blocked) ItemStack.EMPTY else super.slotClick(slot, button, flag, player)
    }
}