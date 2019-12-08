package aurocosh.divinefavor.common.item.compressed_item_drop

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
import net.minecraftforge.items.ItemStackHandler

class CompressedItemsDropProvider : ICapabilityProvider, ICapabilitySerializable<NBTTagCompound> {
    private val inventory: ItemStackHandler = ItemStackHandler(ItemCompressedItemsDrop.InventorySize)

    override fun serializeNBT(): NBTTagCompound {
        return inventory.serializeNBT()
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        inventory.deserializeNBT(nbt)
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability === ITEM_HANDLER_CAPABILITY
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability === ITEM_HANDLER_CAPABILITY)
            ITEM_HANDLER_CAPABILITY.cast<T>(inventory) else null
    }
}