package aurocosh.divinefavor.common.receipes.serialization

import aurocosh.divinefavor.DivineFavor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class RecipeIngredient {
    val item: String = "minecraft:stick"
    val data: Int = 0
    val count: Int = 1

    fun toItemStack(): ItemStack {
        val stackItem = Item.getByNameOrId(item)
        if (stackItem == null) {
            DivineFavor.logger.error("Recipe ingredient not found:$item")
            return ItemStack.EMPTY
        }

        val ingredientStack = ItemStack(stackItem, count)
        ingredientStack.itemDamage = data
        return ingredientStack
    }
}
