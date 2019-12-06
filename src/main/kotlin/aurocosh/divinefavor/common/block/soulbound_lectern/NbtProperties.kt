package aurocosh.divinefavor.common.block.soulbound_lectern

import aurocosh.divinefavor.common.lib.IIndexedEnum
import aurocosh.divinefavor.common.lib.extensions.getBlockState
import aurocosh.divinefavor.common.lib.extensions.setBlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.nbt.NBTTagCompound

class NbtPropertyInt(name: String, defaultValue: Int) : NbtProperty<Int>(name, defaultValue, NBTTagCompound::getInteger, NBTTagCompound::setInteger)
class NbtPropertyString(name: String, defaultValue: String) : NbtProperty<String>(name, defaultValue, NBTTagCompound::getString, NBTTagCompound::setString)
class NbtPropertyIBlockState(name: String, defaultValue: IBlockState) : NbtProperty<IBlockState>(name, defaultValue, NBTTagCompound::getBlockState, NBTTagCompound::setBlockState)
class NbtPropertyNbtTag(name: String, defaultValue: NBTTagCompound) : NbtProperty<NBTTagCompound>(name, defaultValue, NBTTagCompound::getCompoundTag, NBTTagCompound::setTag)

class NbtPropertyEnum<T : Enum<T>>(name: String, defaultValue: T, private val converter: IIndexedEnum<T>) : NbtProperty<T>(name, defaultValue,
        { compound, tag -> converter[compound.getInteger(tag)] },
        { compound, tag, value -> compound.setInteger(tag, value.ordinal) })