package aurocosh.divinefavor.common.item.gem_pouch.capability

import aurocosh.divinefavor.common.item.gem_pouch.capability.GemPouchDataHandler.CAPABILITY_GEM_POUCH
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY

class GemPouchProvider : ICapabilitySerializable<NBTTagCompound> {
    internal var instance = CAPABILITY_GEM_POUCH.defaultInstance as IGemPouchHandler

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return if (capability === CAPABILITY_GEM_POUCH) true else capability === ITEM_HANDLER_CAPABILITY
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (!hasCapability(capability, facing))
            return null
        return when {
            capability === CAPABILITY_GEM_POUCH -> CAPABILITY_GEM_POUCH.cast<T>(instance)
            else -> ITEM_HANDLER_CAPABILITY.cast<T>(instance.getStackHandler())
        }
    }

    override fun serializeNBT(): NBTTagCompound {
        val storage = CAPABILITY_GEM_POUCH.storage
        return storage.writeNBT(CAPABILITY_GEM_POUCH, instance, null) as NBTTagCompound
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        val storage = CAPABILITY_GEM_POUCH.storage
        storage.readNBT(CAPABILITY_GEM_POUCH, instance, null, nbt)
    }
}
