package aurocosh.divinefavor.common.integrations.jei

import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe
import com.google.common.collect.ImmutableList
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.ingredients.VanillaTypes
import mezz.jei.api.recipe.IRecipeWrapper
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import java.util.*

class ImmaterialMediumWrapper(val recipe: ImmaterialMediumRecipe) : IRecipeWrapper {
    override fun getIngredients(ingredients: IIngredients) {
        val ingredientStacks = recipe.ingredients.flatMap { it.getMatchingStacks().asIterable() }
        val callingStoneStacks = recipe.callingStones.flatMap { it.getMatchingStacks().asIterable() }
        val pouchStacks = Ingredient.fromItem(ModItems.ritual_pouch).getMatchingStacks()

        val allStacks = ingredientStacks.S + callingStoneStacks.S + pouchStacks.S
        val stacksImmutable = Collections.unmodifiableList(allStacks.toList())

        ingredients.setInputs(VanillaTypes.ITEM, stacksImmutable)
        ingredients.setOutput(VanillaTypes.ITEM, recipe.result)
    }
}