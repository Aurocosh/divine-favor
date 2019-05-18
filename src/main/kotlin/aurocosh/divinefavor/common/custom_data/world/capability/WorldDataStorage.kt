package aurocosh.divinefavor.common.custom_data.world.capability

import aurocosh.divinefavor.common.custom_data.world.data.altars.AltarsDataSerializer
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

// Handles the actual read/write of the nbt.
class WorldDataStorage : Capability.IStorage<IWorldDataHandler> {

    override fun writeNBT(capability: Capability<IWorldDataHandler>, instance: IWorldDataHandler, side: EnumFacing?): NBTBase? {
        val tag = NBTTagCompound()
        ALTARS_DATA_SERIALIZER.serialize(tag, instance.altarsData)
        return tag
    }

    override fun readNBT(capability: Capability<IWorldDataHandler>, instance: IWorldDataHandler, side: EnumFacing?, nbt: NBTBase) {
        val tag = nbt as NBTTagCompound
        ALTARS_DATA_SERIALIZER.deserialize(tag, instance.altarsData)
    }

    companion object {
        private val ALTARS_DATA_SERIALIZER = AltarsDataSerializer()
    }
}
