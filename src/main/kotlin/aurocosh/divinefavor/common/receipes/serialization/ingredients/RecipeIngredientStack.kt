package aurocosh.divinefavor.common.receipes.serialization.ingredients

import aurocosh.divinefavor.DivineFavor
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient

class RecipeIngredientStack : RecipeIngredient() {
    val item: String = "minecraft:stick"
    val data: Int = 0
    val count: Int = 1

    override fun toIngredient(): Ingredient? {
        val stackItem = Item.getByNameOrId(item)
        if (stackItem == null) {
            DivineFavor.logger.error("Recipe ingredient not found:$item")
            return null
        }

        val ingredientStack = ItemStack(stackItem, count)
        ingredientStack.itemDamage = data
        return Ingredient.fromStacks(ingredientStack)
    }
}
