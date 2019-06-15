package aurocosh.divinefavor.common.talisman_properties

import aurocosh.divinefavor.common.lib.extensions.compound
import aurocosh.divinefavor.common.lib.extensions.getBlockState
import aurocosh.divinefavor.common.lib.extensions.setBlockState
import aurocosh.divinefavor.common.network.message.sever.MessageSyncTalismanPropertyIBlockState
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class TalismanPropertyIBlockState(name: String, defaultValue: IBlockState, onPropertyChanged: (ItemStack) -> Unit) : TalismanProperty<IBlockState>(name, defaultValue, onPropertyChanged) {
    override fun getValueImpl(stack: ItemStack): IBlockState {
        return stack.compound.getBlockState(tag)
    }

    override fun setValueImpl(stack: ItemStack, value: IBlockState) {
        stack.compound.setBlockState(tag, value)
    }

    override fun syncToServer(value: IBlockState) {
        MessageSyncTalismanPropertyIBlockState(name, value).send()
    }

    @SideOnly(Side.CLIENT)
    override fun toLocalString(stack: ItemStack): String {
        val value = getValue(stack)
        return I18n.format(displayKey, value.block.localizedName)
    }
}
