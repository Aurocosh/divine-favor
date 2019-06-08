package aurocosh.divinefavor.common.item.talisman_tools.spell_blade.capability

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.items.CapabilityItemHandler

class SpellBladeProvider : ICapabilitySerializable<NBTTagCompound> {
    internal var instance = SpellBladeDataHandler.CAPABILITY_SPELL_BLADE.defaultInstance as ISpellBladeHandler

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return if (capability === SpellBladeDataHandler.CAPABILITY_SPELL_BLADE) true else capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        if (!hasCapability(capability, facing))
            return null
        return when {
            capability === SpellBladeDataHandler.CAPABILITY_SPELL_BLADE -> SpellBladeDataHandler.CAPABILITY_SPELL_BLADE.cast<T>(instance)
            else -> CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast<T>(instance.getStackHandler())
        }
    }

    override fun serializeNBT(): NBTTagCompound {
        val storage = SpellBladeDataHandler.CAPABILITY_SPELL_BLADE.storage
        return storage.writeNBT(SpellBladeDataHandler.CAPABILITY_SPELL_BLADE, instance, null) as NBTTagCompound
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        val storage = SpellBladeDataHandler.CAPABILITY_SPELL_BLADE.storage
        storage.readNBT(SpellBladeDataHandler.CAPABILITY_SPELL_BLADE, instance, null, nbt)
    }
}
