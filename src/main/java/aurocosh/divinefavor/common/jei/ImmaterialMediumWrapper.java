package aurocosh.divinefavor.common.jei;

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
        for (Ingredient ing : this.recipe.ingredients)
            builder.add(ing.getMatchingStacks());
        ingredients.setInputs(VanillaTypes.ITEM, builder.build());
        ingredients.setOutput(VanillaTypes.ITEM, this.recipe.result);
    }
}