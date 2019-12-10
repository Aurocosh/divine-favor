package aurocosh.divinefavor.common.block.soulbound_lectern

import net.minecraft.nbt.NBTTagCompound

abstract class NbtProperty<T>(val name: String, val defaultValue: T, private val reader: (NBTTagCompound, String) -> T, private val writer: (NBTTagCompound, String, T) -> Unit, val oldNames: List<String>) {
    val allNames = listOf(name) + oldNames

    fun getValue(compound: NBTTagCompound): T {
        val foundName = allNames.firstOrNull(compound::hasKey) ?: return defaultValue
        return reader.invoke(compound, foundName)
    }

    fun setValue(compound: NBTTagCompound, value: T): Boolean {
        writer.invoke(compound, name, value)
        return true
    }
}