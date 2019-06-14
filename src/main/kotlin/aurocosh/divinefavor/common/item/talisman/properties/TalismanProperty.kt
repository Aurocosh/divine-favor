package aurocosh.divinefavor.common.item.talisman.properties

import aurocosh.divinefavor.common.core.ResourceNamer
import net.minecraft.item.ItemStack

abstract class TalismanProperty<T>(val name: String, val defaultValue: T) {
    val tag = "Property$name"
    val tooltipKey = "tooltip.${ResourceNamer.getNameString("property", name)}"
    val displayKey = "name.${ResourceNamer.getNameString("property", name)}"

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
        return true
    }

    abstract fun getValueImpl(stack: ItemStack): T
    abstract fun setValueImpl(stack: ItemStack, value: T)

    abstract fun next(stack: ItemStack): T
    abstract fun previous(stack: ItemStack): T
}