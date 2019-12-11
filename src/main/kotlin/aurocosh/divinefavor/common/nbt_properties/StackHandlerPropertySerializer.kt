package aurocosh.divinefavor.common.nbt_properties

import aurocosh.divinefavor.common.lib.extensions.isPropertySet
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializable
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.items.ItemStackHandler

class StackHandlerPropertySerializer(private val nbtProperty: NbtPropertyNbtTag, private val stackHandler: ItemStackHandler) : INbtSerializable {
    override fun writeToNbt(compound: NBTTagCompound) {
        nbtProperty.setValue(compound, stackHandler.serializeNBT())
    }

    override fun readFromNbt(compound: NBTTagCompound) {
        if(compound.isPropertySet(nbtProperty))
            stackHandler.deserializeNBT(nbtProperty.getValue(compound))
    }
}