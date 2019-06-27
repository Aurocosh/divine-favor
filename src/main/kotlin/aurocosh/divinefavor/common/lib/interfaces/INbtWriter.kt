package aurocosh.divinefavor.common.lib.interfaces

import net.minecraft.nbt.NBTTagCompound

interface INbtWriter<T> {
    fun serialize(nbt: NBTTagCompound, tag: String, instance: T)
    fun deserialize(nbt: NBTTagCompound, tag: String): T
}
