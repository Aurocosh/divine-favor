package aurocosh.divinefavor.common.custom_data.player.data.favor

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import net.minecraft.nbt.NBTTagCompound

// Handles the actual read/write of the nbt.
class SpiritDataSerializer : INbtSerializer<SpiritData> {

    override fun serialize(nbt: NBTTagCompound, instance: SpiritData) {
        val tag = NBTTagCompound()
        tag.setTag(TAG_FAVOR_VALUES, getFavorTag(instance))
        tag.setTag(TAG_FAVOR_INFINITE, getInfinityTag(instance))
        tag.setTag(TAG_CONTRACTS, instance.serializeContracts())
        nbt.setTag(TAG_SPIRIT_DATA, tag)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: SpiritData) {
        if (!nbt.hasKey(TAG_SPIRIT_DATA))
            return
        val spiritTag = nbt.getCompoundTag(TAG_SPIRIT_DATA)
        if (spiritTag.hasKey(TAG_CONTRACTS))
            instance.deserializeContracts(spiritTag.getCompoundTag(TAG_CONTRACTS))
        setFavorFromTag(spiritTag.getCompoundTag(TAG_FAVOR_VALUES), instance)
        setInfinityFromTag(spiritTag.getCompoundTag(TAG_FAVOR_INFINITE), instance)
    }

    companion object {
        private const val TAG_SPIRIT_DATA = "SpiritData"
        private const val TAG_CONTRACTS = "Contracts"
        private const val TAG_FAVOR_VALUES = "FavorValues"
        private const val TAG_FAVOR_INFINITE = "FavorInfinite"

        fun getFavorTag(instance: SpiritData): NBTTagCompound {
            val tag = NBTTagCompound()
            for (spirit in ModMappers.spirits.values)
                tag.setInteger(spirit.name, instance.getFavor(spirit.id))
            return tag
        }

        fun setFavorFromTag(nbt: NBTTagCompound, instance: SpiritData) {
            for (spirit in ModMappers.spirits.values)
                instance.setFavor(spirit.id, nbt.getInteger(spirit.name))
        }

        fun getInfinityTag(instance: SpiritData): NBTTagCompound {
            val tag = NBTTagCompound()
            for (spirit in ModMappers.spirits.values)
                tag.setBoolean(spirit.name, instance.isFavorInfinite(spirit.id))
            return tag
        }

        fun setInfinityFromTag(nbt: NBTTagCompound, instance: SpiritData) {
            for (spirit in ModMappers.spirits.values)
                instance.setFavorInfinite(spirit.id, nbt.getBoolean(spirit.name))
        }
    }
}
