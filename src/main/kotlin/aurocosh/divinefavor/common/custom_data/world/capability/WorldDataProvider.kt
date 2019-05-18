package aurocosh.divinefavor.common.custom_data.world.capability

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class WorldDataProvider : ICapabilitySerializable<NBTTagCompound> {
    internal var instance = WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE!!.defaultInstance

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability === WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (hasCapability(capability, facing)) WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE!!.cast<T>(instance) else null
    }

    override fun serializeNBT(): NBTTagCompound {
        return WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE!!.storage.writeNBT(WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE, instance, null) as NBTTagCompound
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE!!.storage.readNBT(WorldDataDataHandler.CAPABILITY_WORLD_DATA_DIVINE, instance, null, nbt)
    }
}
