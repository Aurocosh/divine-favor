package aurocosh.divinefavor.common.block.soulbound_lectern

import net.minecraft.nbt.NBTTagCompound

abstract class NbtProperty<T>(val name: String, val defaultValue: T, private val reader: (NBTTagCompound, String) -> T, private val writer: (NBTTagCompound, String, T) -> Unit) {
    val tag = "tag_$name"

    fun getValue(compound: NBTTagCompound): T {
        if (!compound.hasKey(tag))
            return defaultValue
        return reader.invoke(compound, tag)
    }

    fun setValue(compound: NBTTagCompound, value: T): Boolean {
        writer.invoke(compound, tag, value)
        return true
    }
}