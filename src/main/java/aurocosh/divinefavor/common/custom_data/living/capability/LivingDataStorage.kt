package aurocosh.divinefavor.common.custom_data.living.capability

import aurocosh.divinefavor.common.custom_data.living.data.curse.CurseDataSerializer
import aurocosh.divinefavor.common.custom_data.living.data.limp_leg.LimpLegDataSerializer
import aurocosh.divinefavor.common.custom_data.living.data.petrification.PetrificationDataSerializer
import aurocosh.divinefavor.common.custom_data.living.data.soul_theft.SoulTheftDataSerializer
import aurocosh.divinefavor.common.custom_data.living.data.suffocating_fumes.SuffocatingFumesDataSerializer
import aurocosh.divinefavor.common.custom_data.living.data.wind_leash.WindLeashDataSerializer
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

// Handles the actual read/write of the nbt.
class LivingDataStorage : Capability.IStorage<ILivingDataHandler> {

    override fun writeNBT(capability: Capability<ILivingDataHandler>, instance: ILivingDataHandler, side: EnumFacing?): NBTBase? {
        val tag = NBTTagCompound()
        CURSE_DATA_SERIALIZER.serialize(tag, instance.curseData)
        LIMP_LEG_DATA_SERIALIZER.serialize(tag, instance.limpLegData)
        PETRIFICATION_DATA_SERIALIZER.serialize(tag, instance.petrificationData)
        SOUL_THEFT_DATA_SERIALIZER.serialize(tag, instance.soulTheftData)
        SUFFOCATING_FUMES_DATA_SERIALIZER.serialize(tag, instance.suffocatingFumesData)
        WIND_LEASH_DATA_SERIALIZER.serialize(tag, instance.windLeashData)
        return tag
    }

    override fun readNBT(capability: Capability<ILivingDataHandler>, instance: ILivingDataHandler, side: EnumFacing?, nbt: NBTBase) {
        val tag = nbt as NBTTagCompound
        CURSE_DATA_SERIALIZER.deserialize(tag, instance.curseData)
        LIMP_LEG_DATA_SERIALIZER.deserialize(tag, instance.limpLegData)
        PETRIFICATION_DATA_SERIALIZER.deserialize(tag, instance.petrificationData)
        SOUL_THEFT_DATA_SERIALIZER.deserialize(tag, instance.soulTheftData)
        SUFFOCATING_FUMES_DATA_SERIALIZER.deserialize(tag, instance.suffocatingFumesData)
        WIND_LEASH_DATA_SERIALIZER.deserialize(tag, instance.windLeashData)
    }

    companion object {
        private val CURSE_DATA_SERIALIZER = CurseDataSerializer()
        private val LIMP_LEG_DATA_SERIALIZER = LimpLegDataSerializer()
        private val PETRIFICATION_DATA_SERIALIZER = PetrificationDataSerializer()
        private val SOUL_THEFT_DATA_SERIALIZER = SoulTheftDataSerializer()
        private val SUFFOCATING_FUMES_DATA_SERIALIZER = SuffocatingFumesDataSerializer()
        private val WIND_LEASH_DATA_SERIALIZER = WindLeashDataSerializer()
    }
}
