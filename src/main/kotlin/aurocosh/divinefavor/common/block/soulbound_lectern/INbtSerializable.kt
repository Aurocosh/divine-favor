package aurocosh.divinefavor.common.block.soulbound_lectern

import net.minecraft.nbt.NBTTagCompound

interface INbtSerializable {
    fun writeToNbt(compound: NBTTagCompound)
    fun readFromNbt(compound: NBTTagCompound)
}