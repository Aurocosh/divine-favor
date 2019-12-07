package aurocosh.divinefavor.common.receipes.dynamic

import aurocosh.divinefavor.common.lib.extensions.asSequence
import aurocosh.divinefavor.common.lib.extensions.isNotEmpty
import aurocosh.divinefavor.common.receipes.base.ModRecipe
import aurocosh.divinefavor.common.stack_properties.interfaces.IPropertyContainer
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.world.World

abstract class StabilizerRecipe(name: String) : ModRecipe(name) {
    override fun canFit(width: Int, height: Int): Boolean {
        return width * height >= 2
    }

    override fun getRecipeOutput(): ItemStack {
        return ItemStack.EMPTY
    }

    override fun getCraftingResult(inv: InventoryCrafting): ItemStack {
        val gem = inv.asSequence().filter(ItemStack::isNotEmpty).first { isUnstableGem(it.item) }
        val gemItem = gem.item
        if (gemItem !is IPropertyContainer)
            return gem

        val result = ItemStack(getStableGem(gemItem))
        gemItem.properties.copy(gem, result)
        return result
    }

    override fun matches(inv: InventoryCrafting, world: World?): Boolean {
        var gemCount = 0
        var otherCount = 0
        var stabilizerCount = 0

        val stacks = inv.asSequence().filter(ItemStack::isNotEmpty)
        for (stack in stacks) {
            when {
                isStabilizer(stack.item) -> stabilizerCount++
                isUnstableGem(stack.item) -> gemCount++
                else -> otherCount++
            }
        }

        return stabilizerCount == 1 && gemCount == 1 && otherCount == 0
    }

    override fun getRemainingItems(inv: InventoryCrafting): NonNullList<ItemStack> {
        return NonNullList.withSize(inv.sizeInventory, ItemStack.EMPTY)
    }

    protected abstract fun isStabilizer(item: Item): Boolean
    protected abstract fun isUnstableGem(item: Item): Boolean
    protected abstract fun getStableGem(item: Item): Item
}