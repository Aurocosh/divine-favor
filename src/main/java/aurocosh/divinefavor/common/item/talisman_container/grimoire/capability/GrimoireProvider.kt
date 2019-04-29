package aurocosh.divinefavor.common.item.talisman_container.grimoire.capability

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler

class GrimoireProvider : ICapabilitySerializable<NBTTagCompound> {
    internal var instance = GrimoireDataHandler.CAPABILITY_GRIMOIRE!!.defaultInstance

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return if (capability === GrimoireDataHandler.CAPABILITY_GRIMOIRE) true else capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (!hasCapability(capability, facing))
            return null
        return if (capability === GrimoireDataHandler.CAPABILITY_GRIMOIRE) GrimoireDataHandler.CAPABILITY_GRIMOIRE.cast<T>(instance) else CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast<T>(instance!!.getStackHandler())
    }

    override fun serializeNBT(): NBTTagCompound {
        val storage = GrimoireDataHandler.CAPABILITY_GRIMOIRE!!.storage
        return storage.writeNBT(GrimoireDataHandler.CAPABILITY_GRIMOIRE, instance, null) as NBTTagCompound
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        val storage = GrimoireDataHandler.CAPABILITY_GRIMOIRE!!.storage
        storage.readNBT(GrimoireDataHandler.CAPABILITY_GRIMOIRE, instance, null, nbt)
    }
}
