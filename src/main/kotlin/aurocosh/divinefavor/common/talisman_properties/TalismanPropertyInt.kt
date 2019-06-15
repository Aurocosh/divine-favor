package aurocosh.divinefavor.common.talisman_properties

import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyInt
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class TalismanPropertyInt(name: String, defaultValue: Int, val minValue: Int, val maxValue: Int, onPropertyChanged: (ItemStack) -> Unit) : TalismanProperty<Int>(name, defaultValue, onPropertyChanged) {
    override fun getValueImpl(stack: ItemStack): Int {
        return stack.compound.getInteger(tag)
    }

    override fun setValueImpl(stack: ItemStack, value: Int) {
        stack.compound.setInteger(tag, MathHelper.clamp(value, minValue, maxValue))
    }

    override fun syncToServer(value: Int) {
        MessageSyncTalismanPropertyInt(name, value).send()
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        return I18n.format(displayKey, value)
    }
}
