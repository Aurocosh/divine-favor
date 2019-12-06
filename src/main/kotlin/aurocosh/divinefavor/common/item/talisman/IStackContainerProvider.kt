package aurocosh.divinefavor.common.item.talisman

import aurocosh.divinefavor.common.item.talisman_tools.IStackContainer
import net.minecraft.item.ItemStack

interface IStackContainerProvider : ISelectedStackProvider {
    fun getStackContainer(stack: ItemStack): IStackContainer
}