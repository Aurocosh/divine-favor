package aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability

import aurocosh.divinefavor.common.item.talisman_tools.grimoire.capability.GrimoireDataHandler.CAPABILITY_GRIMOIRE
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY

class GrimoireProvider : ICapabilitySerializable<NBTTagCompound> {
    internal var instance = CAPABILITY_GRIMOIRE.defaultInstance as IGrimoireHandler

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return if (capability === CAPABILITY_GRIMOIRE) true else capability === ITEM_HANDLER_CAPABILITY
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (!hasCapability(capability, facing))
            return null
        return when {
            capability === CAPABILITY_GRIMOIRE -> CAPABILITY_GRIMOIRE.cast<T>(instance)
            else -> ITEM_HANDLER_CAPABILITY.cast<T>(instance.getStackHandler())
        }
    }

    override fun serializeNBT(): NBTTagCompound {
        val storage = CAPABILITY_GRIMOIRE.storage
        return storage.writeNBT(CAPABILITY_GRIMOIRE, instance, null) as NBTTagCompound
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        val storage = CAPABILITY_GRIMOIRE.storage
        storage.readNBT(CAPABILITY_GRIMOIRE, instance, null, nbt)
    }
}
