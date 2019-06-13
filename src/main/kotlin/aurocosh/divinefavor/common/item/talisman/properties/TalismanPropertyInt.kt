package aurocosh.divinefavor.common.item.talisman.properties

import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper

class TalismanPropertyInt(name: String, prefix: String, suffix: String, defaultValue: Int, val minValue: Int, val maxValue: Int) : TalismanProperty<Int>(name, prefix, suffix, defaultValue) {

    override fun getValueImpl(stack: ItemStack): Int {
        return stack.compound.getInteger(tag)
    }

    override fun setValueImpl(stack: ItemStack, value: Int) {
        stack.compound.setInteger(tag, MathHelper.clamp(value, minValue, maxValue))
    }

    override fun next(stack: ItemStack): Int {
        return getValue(stack) + 1
    }

    override fun previous(stack: ItemStack): Int {
        return getValue(stack) - 1
    }
}
