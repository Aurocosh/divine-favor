package aurocosh.divinefavor.common.custom_data.living.capability

import aurocosh.divinefavor.common.lib.ICapabilitySerializableAnnotationFix
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

class LivingDataProvider : ICapabilitySerializableAnnotationFix<NBTTagCompound> {
    internal var instance = LivingDataDataHandler.CAPABILITY_LIVING_DATA.defaultInstance

    override fun hasCapability(capability: Capability<*>?, facing: EnumFacing?): Boolean {
        return capability === LivingDataDataHandler.CAPABILITY_LIVING_DATA
    }

    override fun <T> getCapability(capability: Capability<T>?, facing: EnumFacing?): T? {
        return if (hasCapability(capability, facing)) LivingDataDataHandler.CAPABILITY_LIVING_DATA.cast<T>(instance) else null
    }

    override fun serializeNBT(): NBTTagCompound {
        return (LivingDataDataHandler.CAPABILITY_LIVING_DATA.storage.writeNBT(LivingDataDataHandler.CAPABILITY_LIVING_DATA, instance, null) as NBTTagCompound)
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        LivingDataDataHandler.CAPABILITY_LIVING_DATA.storage.readNBT(LivingDataDataHandler.CAPABILITY_LIVING_DATA, instance, null, nbt)
    }
}
