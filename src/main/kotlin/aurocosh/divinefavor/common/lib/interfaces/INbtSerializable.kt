package aurocosh.divinefavor.common.lib.interfaces

import net.minecraft.nbt.NBTTagCompound

interface INbtSerializable {
    fun writeToNbt(compound: NBTTagCompound)
    fun readFromNbt(compound: NBTTagCompound)
}