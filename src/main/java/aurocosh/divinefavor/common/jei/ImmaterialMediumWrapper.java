package aurocosh.divinefavor.common.jei;

import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class ImmaterialMediumWrapper implements IRecipeWrapper {
    public final ImmaterialMediumRecipe recipe;

    public ImmaterialMediumWrapper(ImmaterialMediumRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        for (Ingredient ingredient : recipe.ingredients)
            builder.add(ingredient.getMatchingStacks());
        builder.add(recipe.callingStone.getMatchingStacks());
        builder.add(Ingredient.fromItem(ModItems.ritual_pouch).getMatchingStacks());

        ingredients.setInputs(VanillaTypes.ITEM, builder.build());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.result);
    }
}