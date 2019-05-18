package aurocosh.divinefavor.common.integrations.jei

import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe
import com.google.common.collect.ImmutableList
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.ingredients.VanillaTypes
import mezz.jei.api.recipe.IRecipeWrapper
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient

class ImmaterialMediumWrapper(val recipe: ImmaterialMediumRecipe) : IRecipeWrapper {

    override fun getIngredients(ingredients: IIngredients) {
        val builder = ImmutableList.builder<ItemStack>()
        for (ingredient in recipe.ingredients)
            builder.add(*ingredient.getMatchingStacks())
        builder.add(*recipe.callingStone.getMatchingStacks())
        builder.add(*Ingredient.fromItem(ModItems.ritual_pouch).getMatchingStacks())

        ingredients.setInputs(VanillaTypes.ITEM, builder.build())
        ingredients.setOutput(VanillaTypes.ITEM, recipe.result)
    }
}