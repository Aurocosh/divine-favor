package aurocosh.divinefavor.common.custom_data.player.data.favor

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class SpiritDataSerializer : INbtSerializer<SpiritData> {

    override fun serialize(nbt: NBTTagCompound, instance: SpiritData) {
        val tag = NBTTagCompound()
        for (spirit in ModMappers.spirits.values)
            tag.setInteger(spirit.name, instance.getFavor(spirit.id))
        tag.setTag(TAG_CONTRACTS, instance.serializeContracts())
        nbt.setTag(TAG_SPIRIT_DATA, tag)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: SpiritData) {
        if (!nbt.hasKey(TAG_SPIRIT_DATA))
            return
        val usesTag = nbt.getCompoundTag(TAG_SPIRIT_DATA)
        if (usesTag.hasKey(TAG_CONTRACTS))
            instance.deserializeContracts(usesTag.getCompoundTag(TAG_CONTRACTS))
        for (spirit in ModMappers.spirits.values)
            instance.setFavor(spirit.id, usesTag.getInteger(spirit.name))
    }

    companion object {
        private const val TAG_SPIRIT_DATA = "SpiritData"
        private const val TAG_CONTRACTS = "Contracts"
    }
}
