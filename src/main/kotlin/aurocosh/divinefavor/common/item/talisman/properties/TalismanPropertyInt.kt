package aurocosh.divinefavor.common.item.talisman.properties

import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyInt
import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper

class TalismanPropertyInt(name: String, prefix: String, suffix: String, defaultValue: Int, val minValue: Int, val maxValue: Int) : TalismanProperty<Int>(name, prefix, suffix, defaultValue) {

    override fun getValue(stack: ItemStack): Int {
        if (!stack.hasTagCompound())
            return defaultValue
        return stack.compound.getInteger(tag)
    }

    override fun setValue(stack: ItemStack, value: Int) {
        stack.compound.setInteger(tag, MathHelper.clamp(value, minValue, maxValue))
    }

    override fun setValueAndSync(stack: ItemStack, value: Int, playerSlot: Int) {
        val current = getValue(stack)
        if (current == value)
            return

        setValue(stack, value)
        syncToServer(playerSlot, value)
    }

    override fun syncToServer(playerSlot: Int, value: Int) {
        MessageSyncTalismanPropertyInt(playerSlot, name, value).send()
    }
}
