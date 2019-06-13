package aurocosh.divinefavor.common.item.talisman.properties

import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyBool
import net.minecraft.item.ItemStack

class TalismanPropertyBool(name: String, prefix: String, tooltip: String, defaultValue: Boolean) : TalismanProperty<Boolean>(name, prefix, tooltip, defaultValue) {

    override fun getValue(stack: ItemStack): Boolean {
        if (!stack.hasTagCompound())
            return defaultValue
        return stack.compound.getBoolean(tag)
    }

    override fun setValue(stack: ItemStack, value: Boolean) {
        stack.compound.setBoolean(tag, value)
    }

    override fun setValueAndSync(stack: ItemStack, value: Boolean, playerSlot: Int) {
        val current = getValue(stack)
        if (current == value)
            return

        setValue(stack, value)
        syncToServer(playerSlot, value)
    }

    override fun syncToServer(playerSlot: Int, value: Boolean) {
        MessageSyncTalismanPropertyBool(playerSlot, name, value).send()
    }
}
