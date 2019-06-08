package aurocosh.divinefavor.common.item.talisman_container

import net.minecraft.item.ItemStack

interface ITalismanTool {
    fun getTalismanContainer(stack: ItemStack): ITalismanContainer?
}