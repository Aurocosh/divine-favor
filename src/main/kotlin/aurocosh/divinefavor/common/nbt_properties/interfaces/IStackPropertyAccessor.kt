package aurocosh.divinefavor.common.nbt_properties.interfaces

import aurocosh.divinefavor.common.nbt_properties.NbtProperty
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

interface INbtPropertyAccessor {
    val list: List<NbtProperty<out Any>>

    fun get(name: String): NbtProperty<out Any>?
    fun exist(name: String): Boolean
    fun exist(property: NbtProperty<out Any>): Boolean
}