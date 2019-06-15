package aurocosh.divinefavor.common.talisman_properties

import aurocosh.divinefavor.common.constants.ConstLang
import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyBool
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class TalismanPropertyBool(name: String, defaultValue: Boolean, onPropertyChanged: (ItemStack) -> Unit) : TalismanProperty<Boolean>(name, defaultValue, onPropertyChanged) {
    override fun getValueImpl(stack: ItemStack): Boolean {
        return stack.compound.getBoolean(tag)
    }

    override fun setValueImpl(stack: ItemStack, value: Boolean) {
        stack.compound.setBoolean(tag, value)
    }

    override fun syncToServer(value: Boolean) {
        MessageSyncTalismanPropertyBool(name, value).send()
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        val valueKey = if (value) ConstLang.yesKey else ConstLang.noKey
        val valueString = I18n.format(valueKey)
        return I18n.format(displayKey, valueString)
    }
}
