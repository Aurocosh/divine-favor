package aurocosh.divinefavor.common.custom_data

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound

class CompositeSerializer<T, K>(private val serializer: INbtSerializer<K>, private val converter: (T) -> K) : INbtSerializer<T> {
    override fun serialize(nbt: NBTTagCompound, instance: T) {
        serializer.serialize(nbt, converter.invoke(instance))
    }

    override fun deserialize(nbt: NBTTagCompound, instance: T) {
        serializer.deserialize(nbt, converter.invoke(instance))
    }
}