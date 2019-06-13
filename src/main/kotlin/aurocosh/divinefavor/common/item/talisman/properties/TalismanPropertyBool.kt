package aurocosh.divinefavor.common.item.talisman.properties

import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyBool
import net.minecraft.item.ItemStack

class TalismanPropertyBool(name: String, prefix: String, tooltip: String, defaultValue: Boolean) : TalismanProperty<Boolean>(name, prefix, tooltip, defaultValue) {
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
