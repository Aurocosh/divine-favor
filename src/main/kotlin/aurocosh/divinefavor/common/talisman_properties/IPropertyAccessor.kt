package aurocosh.divinefavor.common.talisman_properties

import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

interface IPropertyAccessor {
    val list: List<TalismanProperty<out Any>>

    fun get(index: Int): TalismanProperty<out Any>
    fun get(name: String): TalismanProperty<out Any>?

    fun exist(index: Int): Boolean
    fun exist(name: String): Boolean

    fun getSelectedIndex(stack: ItemStack): Int
    fun setSelectedIndex(stack: ItemStack, index: Int)

    @SideOnly(Side.CLIENT)
    fun getPropertyTooltip(stack: ItemStack): List<String>
}