package aurocosh.divinefavor.common.nbt_properties

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializable
import net.minecraft.nbt.NBTTagCompound

open class NbtPropertySerializer : INbtSerializable {
    private val serializers = ArrayList<INbtSerializable>()

    fun registerSerializer(serializable: INbtSerializable) = serializers.add(serializable)

    override fun writeToNbt(compound: NBTTagCompound) = serializers.forEach { it.writeToNbt(compound) }
    override fun readFromNbt(compound: NBTTagCompound) = serializers.forEach { it.readFromNbt(compound) }
}