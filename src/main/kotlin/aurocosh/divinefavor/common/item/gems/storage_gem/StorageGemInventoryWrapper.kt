package aurocosh.divinefavor.common.item.gems.storage_gem

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent

class StorageGemInventoryWrapper(private val inventory: IInventory) : IInventory {

    override fun getSizeInventory(): Int {
        return inventory.sizeInventory
    }

    override fun isEmpty(): Boolean {
        return inventory.isEmpty
    }

    override fun getStackInSlot(index: Int): ItemStack {
        return inventory.getStackInSlot(index)
    }

    override fun decrStackSize(index: Int, count: Int): ItemStack {
        return inventory.decrStackSize(index, count)
    }

    override fun removeStackFromSlot(index: Int): ItemStack {
        return inventory.removeStackFromSlot(index)
    }

    override fun setInventorySlotContents(index: Int, stack: ItemStack) {
        inventory.setInventorySlotContents(index, stack)
    }

    override fun getInventoryStackLimit(): Int {
        return inventory.inventoryStackLimit
    }

    override fun markDirty() {
        inventory.markDirty()
    }

    override fun isUsableByPlayer(player: EntityPlayer): Boolean {
        return !player.isDead
    }

    override fun openInventory(player: EntityPlayer) {
        inventory.openInventory(player)
    }

    override fun closeInventory(player: EntityPlayer) {
        inventory.closeInventory(player)
    }

    override fun isItemValidForSlot(index: Int, stack: ItemStack): Boolean {
        return inventory.isItemValidForSlot(index, stack)
    }

    override fun getField(id: Int): Int {
        return inventory.getField(id)
    }

    override fun setField(id: Int, value: Int) {
        inventory.setField(id, value)
    }

    override fun getFieldCount(): Int {
        return inventory.fieldCount
    }

    override fun clear() {
        inventory.clear()
    }

    override fun getName(): String {
        return inventory.name
    }

    override fun hasCustomName(): Boolean {
        return inventory.hasCustomName()
    }

    override fun getDisplayName(): ITextComponent {
        return inventory.displayName
    }
}
