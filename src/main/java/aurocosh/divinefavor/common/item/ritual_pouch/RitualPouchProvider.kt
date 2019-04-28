package aurocosh.divinefavor.common.item.ritual_pouch

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class RitualPouchProvider : ICapabilityProvider, ICapabilitySerializable<NBTTagCompound> {
    private val inventory: ItemStackHandler = ItemStackHandler(ItemRitualPouch.SIZE)

    override fun serializeNBT(): NBTTagCompound {
        return inventory.serializeNBT()
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        inventory.deserializeNBT(nbt)
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast<T>(inventory) else null
    }
}