package aurocosh.divinefavor.common.item.talisman_tools.spell_pick.capability

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler

class SpellPickProvider : ICapabilitySerializable<NBTTagCompound> {
    internal var instance = SpellPickDataHandler.CAPABILITY_SPELL_PICK.defaultInstance as ISpellPickHandler

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return if (capability === SpellPickDataHandler.CAPABILITY_SPELL_PICK) true else capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (!hasCapability(capability, facing))
            return null
        return when {
            capability === SpellPickDataHandler.CAPABILITY_SPELL_PICK -> SpellPickDataHandler.CAPABILITY_SPELL_PICK.cast<T>(instance)
            else -> CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast<T>(instance.getStackHandler())
        }
    }

    override fun serializeNBT(): NBTTagCompound {
        val storage = SpellPickDataHandler.CAPABILITY_SPELL_PICK.storage
        return storage.writeNBT(SpellPickDataHandler.CAPABILITY_SPELL_PICK, instance, null) as NBTTagCompound
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        val storage = SpellPickDataHandler.CAPABILITY_SPELL_PICK.storage
        storage.readNBT(SpellPickDataHandler.CAPABILITY_SPELL_PICK, instance, null, nbt)
    }
}
