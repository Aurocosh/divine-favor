package aurocosh.divinefavor.common.lib.interfaces

import net.minecraft.nbt.NBTTagCompound

interface INbtSerializer<T> {
    fun serialize(nbt: NBTTagCompound, instance: T)
    fun deserialize(nbt: NBTTagCompound, instance: T)
}
