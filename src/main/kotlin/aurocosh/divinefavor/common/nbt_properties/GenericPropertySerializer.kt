package aurocosh.divinefavor.common.nbt_properties

import aurocosh.divinefavor.common.lib.extensions.isPropertySet
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializable
import net.minecraft.nbt.NBTTagCompound

class GenericPropertySerializer<T : Any>(private val nbtProperty: NbtProperty<T>, private val reader: () -> T, private val writer: (T) -> Unit) : INbtSerializable {
    override fun writeToNbt(compound: NBTTagCompound) {
        nbtProperty.setValue(compound, reader.invoke())
    }

    override fun readFromNbt(compound: NBTTagCompound) {
        if (compound.isPropertySet(nbtProperty))
            writer.invoke(nbtProperty.getValue(compound))
    }
}