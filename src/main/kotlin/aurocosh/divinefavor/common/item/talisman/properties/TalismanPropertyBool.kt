package aurocosh.divinefavor.common.item.talisman.properties

import aurocosh.divinefavor.common.lib.extensions.compound
import net.minecraft.item.ItemStack

class TalismanPropertyBool(name: String, defaultValue: Boolean) : TalismanProperty<Boolean>(name, defaultValue) {
    override fun getValueImpl(stack: ItemStack): Boolean {
        return stack.compound.getBoolean(tag)
    }

    override fun setValueImpl(stack: ItemStack, value: Boolean) {
        stack.compound.setBoolean(tag, value)
    }

    override fun next(stack: ItemStack): Boolean {
        return !getValue(stack)
    }

    override fun previous(stack: ItemStack): Boolean {
        return !getValue(stack)
    }
}
