package aurocosh.divinefavor.common.receipes.dynamic

import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.gems.base.IStackUsableGemItem
import aurocosh.divinefavor.common.item.gems.properties.GemMaskProperties
import aurocosh.divinefavor.common.lib.extensions.*
import aurocosh.divinefavor.common.receipes.base.ModRecipe
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.world.World

class GemMaskRecipe : ModRecipe("gem_mask_recipe") {
    override fun canFit(width: Int, height: Int): Boolean {
        return width * height >= 2
    }

    override fun getRecipeOutput(): ItemStack {
        return ItemStack.EMPTY
    }

    override fun getCraftingResult(inv: InventoryCrafting): ItemStack {
        val stacks = inv.asSequence().filter(ItemStack::isNotEmpty).toList()
        val stacksToMask = stacks.first { it.item is IStackUsableGemItem }
        val stackUsedAsMask = stacks.first { it.item !is IStackUsableGemItem }

        val maskId = stackUsedAsMask.item.regName.toString()
        val maskMeta = stackUsedAsMask.metadata

        val result = stacksToMask.copy()
        result.set(GemMaskProperties.maskItemId, maskId)
        result.set(GemMaskProperties.maskItemMeta, maskMeta)

        return result
    }

    override fun matches(inv: InventoryCrafting, world: World?): Boolean {
        val stacks = inv.asSequence().filter(ItemStack::isNotEmpty).toList()
        if (stacks.size != 2)
            return false

        val stabilizerCount = stacks.S.filter { isStabilizer(it.item) }.count()
        val gemCount = stacks.S.filter { it.item is IStackUsableGemItem }.count()
        return stabilizerCount == 0 && gemCount == 1
    }

    override fun getRemainingItems(inv: InventoryCrafting): NonNullList<ItemStack> {
        val stacks = NonNullList.withSize(inv.sizeInventory, ItemStack.EMPTY)
        for (index in inv.indices) {
            val stack = inv.getStackInSlot(index)
            if (!stack.isEmpty && stack.item !is IStackUsableGemItem) {
                val remainingStack = stack.copy()
                remainingStack.count = 1
                stacks[index] = remainingStack
            }
        }
        return stacks
    }

    private fun isStabilizer(item: Item): Boolean {
        return item === ModItems.gem_stabilizer || item === ModItems.pebble_stabilizer
    }
}