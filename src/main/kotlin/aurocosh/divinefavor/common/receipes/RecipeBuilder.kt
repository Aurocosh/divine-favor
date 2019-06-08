package aurocosh.divinefavor.common.receipes

import aurocosh.divinefavor.common.item.gems.calling_stones.ItemCallingStone
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import java.util.*

class RecipeBuilder(var result: ItemStack, callingStone: ItemCallingStone) {
    private val callingStone: Ingredient = Ingredient.fromStacks(ItemStack(callingStone))
    private val ingredients: MutableList<Ingredient> = ArrayList()

    constructor(result: ItemStack, callingStone: ItemCallingStone, ingredientStacks: List<ItemStack>) : this(result, callingStone) {
        for (stack in ingredientStacks)
            ingredients.add(Ingredient.fromStacks(stack))
    }

    fun addIngredient(item: Item, count: Int): RecipeBuilder {
        ingredients.add(Ingredient.fromStacks(ItemStack(item, count)))
        return this
    }

    fun addIngredient(item: Item): RecipeBuilder {
        ingredients.add(Ingredient.fromItems(item))
        return this
    }

    fun create(): ImmaterialMediumRecipe {
        return ImmaterialMediumRecipe(result, callingStone, ingredients)
    }
}
