package aurocosh.divinefavor.common.item.talisman

import aurocosh.divinefavor.common.item.talisman_tools.ITalismanTool
import net.minecraft.item.ItemStack

interface ITalismanToolContainer {
    fun getTalismanTool(stack: ItemStack): ITalismanTool
}