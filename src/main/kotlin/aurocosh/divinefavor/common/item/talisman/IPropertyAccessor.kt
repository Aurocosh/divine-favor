package aurocosh.divinefavor.common.item.talisman

import aurocosh.divinefavor.common.item.talisman.properties.StackProperty
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

interface IPropertyAccessor {
    val list: List<StackProperty<out Any>>

    fun get(index: Int): StackProperty<out Any>
    fun get(name: String): StackProperty<out Any>?

    fun getSelectedIndex(stack: ItemStack): Int
    fun setSelectedIndex(stack: ItemStack, index: Int)

    @SideOnly(Side.CLIENT)
    fun getPropertyTooltip(stack: ItemStack): List<String>
}