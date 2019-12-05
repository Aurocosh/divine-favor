package aurocosh.divinefavor.common.util

import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import kotlin.math.min

object UtilItemStack {
    fun actionResult(success: Boolean, stack: ItemStack): ActionResult<ItemStack> {
        val result = when {
            success -> EnumActionResult.SUCCESS
            else -> EnumActionResult.PASS
        }
        return ActionResult(result, stack)
    }

    fun actionResultPass(success: Boolean): EnumActionResult {
        return if (success) EnumActionResult.SUCCESS else EnumActionResult.PASS
    }

    fun splitStack(stack: ItemStack, totalCount: Int, desiredStackSize: Int = stack.maxStackSize): MutableList<ItemStack> {
        val stackSize = min(desiredStackSize, stack.maxStackSize)

        val stackCount = totalCount / stackSize;
        val leftover = totalCount % stackSize;

        val template = stack.copy();
        template.count = template.maxStackSize

        val resultStacks = generateSequence(template::copy).take(stackCount).toMutableList()
        if (leftover > 0) {
            template.count = leftover
            resultStacks.add(template)
        }
        return resultStacks
    }
}