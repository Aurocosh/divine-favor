package aurocosh.divinefavor.common.item.talisman_tools.spell_bow.capability

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler

class SpellBowProvider : ICapabilitySerializable<NBTTagCompound> {
    internal var instance = SpellBowDataHandler.CAPABILITY_SPELL_BOW.defaultInstance

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return if (capability === SpellBowDataHandler.CAPABILITY_SPELL_BOW) true else capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (!hasCapability(capability, facing))
            return null
        return if (capability === SpellBowDataHandler.CAPABILITY_SPELL_BOW) SpellBowDataHandler.CAPABILITY_SPELL_BOW.cast<T>(instance) else CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast<T>(instance!!.getStackHandler())
    }

    override fun serializeNBT(): NBTTagCompound {
        val storage = SpellBowDataHandler.CAPABILITY_SPELL_BOW.storage
        return storage.writeNBT(SpellBowDataHandler.CAPABILITY_SPELL_BOW, instance, null) as NBTTagCompound
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        val storage = SpellBowDataHandler.CAPABILITY_SPELL_BOW.storage
        storage.readNBT(SpellBowDataHandler.CAPABILITY_SPELL_BOW, instance, null, nbt)
    }
}
