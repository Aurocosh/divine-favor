package aurocosh.divinefavor.common.util

import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult

object UtilItem {
    fun actionResult(stack: ItemStack, success: Boolean) : ActionResult<ItemStack>
    {
        val result = when {
            success -> EnumActionResult.SUCCESS
            else -> EnumActionResult.PASS
        }
        return ActionResult(result, stack)
    }
}