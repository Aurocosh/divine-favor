package aurocosh.divinefavor.common.talisman_properties

import aurocosh.divinefavor.common.core.ResourceNamer
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

abstract class TalismanProperty<T>(val name: String, val defaultValue: T, private val onPropertyChanged: (ItemStack) -> Unit) {
    val tag = "Property$name"
    val tooltipKey = ResourceNamer.getTypedNameString("tooltip", "property", name)
    val displayKey = ResourceNamer.getTypedNameString("name", "property", name)

    fun getValue(stack: ItemStack): T {
        if (!stack.hasTagCompound())
            return defaultValue
        return getValueImpl(stack)
    }

    fun setValue(stack: ItemStack, value: T): Boolean {
        val current = getValue(stack)
        if (current == value)
            return false
        setValueImpl(stack, value)
        onPropertyChanged.invoke(stack)
        return true
    }

    fun setValueAndSync(stack: ItemStack, value: T) {
        if (setValue(stack, value))
            syncToServer(value)
    }

    abstract fun syncToServer(value: T)

    abstract fun getValueImpl(stack: ItemStack): T
    abstract fun setValueImpl(stack: ItemStack, value: T)

    @SideOnly(Side.CLIENT)
    abstract fun toLocalString(stack: ItemStack): String
}