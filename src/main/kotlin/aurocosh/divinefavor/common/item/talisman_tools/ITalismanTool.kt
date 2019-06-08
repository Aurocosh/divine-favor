package aurocosh.divinefavor.common.item.talisman_tools

import net.minecraft.item.ItemStack

interface ITalismanTool {
    fun getTalismanContainer(stack: ItemStack): ITalismanContainer?
}