package aurocosh.divinefavor.common.custom_data

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

// Handles the actual read/write of the nbt.
class CustomDataStorage<T> : Capability.IStorage<T> {
    private val serializers: MutableList<INbtSerializer<T>> = ArrayList()

    fun <K> addSerializer(serializer: INbtSerializer<K>, converter: (T) -> K) {
        serializers.add(CompositeSerializer(serializer, converter))
    }

    override fun writeNBT(capability: Capability<T>, instance: T, side: EnumFacing?): NBTBase? {
        val compound = NBTTagCompound()
        serializers.forEach { it.serialize(compound, instance) }
        return compound
    }

    override fun readNBT(capability: Capability<T>, instance: T, side: EnumFacing?, nbt: NBTBase) {
        val compound = nbt as NBTTagCompound
        serializers.forEach { it.deserialize(compound, instance) }
    }
}
