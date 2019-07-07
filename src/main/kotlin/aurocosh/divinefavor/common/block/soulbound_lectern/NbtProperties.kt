package aurocosh.divinefavor.common.block.soulbound_lectern

import aurocosh.divinefavor.common.lib.IIndexedEnum
import net.minecraft.nbt.NBTTagCompound

class NbtPropertyInt(name: String, defaultValue: Int) : NbtProperty<Int>(name, defaultValue, NBTTagCompound::getInteger, NBTTagCompound::setInteger)
class NbtPropertyString(name: String, defaultValue: String) : NbtProperty<String>(name, defaultValue, NBTTagCompound::getString, NBTTagCompound::setString)

class NbtPropertyEnum<T : Enum<T>>(name: String, defaultValue: T, private val converter: IIndexedEnum<T>) : NbtProperty<T>(name, defaultValue,
        { compound, tag -> converter[compound.getInteger(tag)] },
        { compound, tag, value -> compound.setInteger(tag, value.ordinal) })