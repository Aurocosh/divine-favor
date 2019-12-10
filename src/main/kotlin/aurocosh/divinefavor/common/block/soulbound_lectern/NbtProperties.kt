package aurocosh.divinefavor.common.block.soulbound_lectern

import aurocosh.divinefavor.common.lib.IIndexedEnum
import aurocosh.divinefavor.common.lib.extensions.getBlockState
import aurocosh.divinefavor.common.lib.extensions.setBlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.nbt.NBTTagCompound

class NbtPropertyInt(name: String, defaultValue: Int, oldNames: List<String> = emptyList()) : NbtProperty<Int>(name, defaultValue, NBTTagCompound::getInteger, NBTTagCompound::setInteger, oldNames)
class NbtPropertyString(name: String, defaultValue: String, oldNames: List<String> = emptyList()) : NbtProperty<String>(name, defaultValue, NBTTagCompound::getString, NBTTagCompound::setString, oldNames)
class NbtPropertyIBlockState(name: String, defaultValue: IBlockState, oldNames: List<String> = emptyList()) : NbtProperty<IBlockState>(name, defaultValue, NBTTagCompound::getBlockState, NBTTagCompound::setBlockState, oldNames)
class NbtPropertyNbtTag(name: String, defaultValue: NBTTagCompound, oldNames: List<String> = emptyList()) : NbtProperty<NBTTagCompound>(name, defaultValue, NBTTagCompound::getCompoundTag, NBTTagCompound::setTag, oldNames)

class NbtPropertyEnum<T : Enum<T>>(name: String, defaultValue: T, private val converter: IIndexedEnum<T>, oldNames: List<String> = emptyList()) : NbtProperty<T>(name, defaultValue,
        { compound, tag -> converter[compound.getInteger(tag)] },
        { compound, tag, value -> compound.setInteger(tag, value.ordinal) }, oldNames)