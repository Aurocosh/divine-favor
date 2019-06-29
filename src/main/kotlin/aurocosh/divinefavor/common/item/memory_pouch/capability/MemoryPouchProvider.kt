package aurocosh.divinefavor.common.item.memory_pouch.capability

import aurocosh.divinefavor.common.item.memory_pouch.capability.MemoryPouchDataHandler.CAPABILITY_MEMORY_POUCH
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY

class MemoryPouchProvider : ICapabilitySerializable<NBTTagCompound> {
    internal var instance = CAPABILITY_MEMORY_POUCH.defaultInstance as IMemoryPouch

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return if (capability === CAPABILITY_MEMORY_POUCH) true else capability === ITEM_HANDLER_CAPABILITY
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (!hasCapability(capability, facing))
            return null
        return when {
            capability === CAPABILITY_MEMORY_POUCH -> CAPABILITY_MEMORY_POUCH.cast<T>(instance)
            else -> ITEM_HANDLER_CAPABILITY.cast<T>(instance.getStackHandler())
        }
    }

    override fun serializeNBT(): NBTTagCompound {
        val storage = CAPABILITY_MEMORY_POUCH.storage
        return storage.writeNBT(CAPABILITY_MEMORY_POUCH, instance, null) as NBTTagCompound
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        val storage = CAPABILITY_MEMORY_POUCH.storage
        storage.readNBT(CAPABILITY_MEMORY_POUCH, instance, null, nbt)
    }
}
