package aurocosh.divinefavor.common.custom_data.player.capability

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class PlayerDataProvider : ICapabilitySerializable<NBTTagCompound> {
    internal var instance = PlayerDataDataHandler.CAPABILITY_PLAYER_DATA!!.defaultInstance

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability === PlayerDataDataHandler.CAPABILITY_PLAYER_DATA
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (hasCapability(capability, facing)) PlayerDataDataHandler.CAPABILITY_PLAYER_DATA!!.cast<T>(instance) else null
    }

    override fun serializeNBT(): NBTTagCompound {
        return (PlayerDataDataHandler.CAPABILITY_PLAYER_DATA!!.storage.writeNBT(PlayerDataDataHandler.CAPABILITY_PLAYER_DATA, instance, null) as NBTTagCompound?)!!
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        PlayerDataDataHandler.CAPABILITY_PLAYER_DATA!!.storage.readNBT(PlayerDataDataHandler.CAPABILITY_PLAYER_DATA, instance, null, nbt)
    }
}
