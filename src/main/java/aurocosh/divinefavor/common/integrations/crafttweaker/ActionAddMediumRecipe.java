package aurocosh.divinefavor.common.integrations.crafttweaker;

import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe;
import aurocosh.divinefavor.common.receipes.ModRecipes;
import com.blamejared.mtlib.helpers.InputHelper;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Arrays;

public class ActionAddMediumRecipe implements IAction {
    private final ImmaterialMediumRecipe[] recipes;

    public ActionAddMediumRecipe(IItemStack output, IIngredient[] callingStones, IIngredient[] inputs) {
        ItemStack outputStack = InputHelper.toStack(output);
        Ingredient[] stones = Arrays.stream(callingStones).map(CraftTweakerMC::getIngredient).toArray(Ingredient[]::new);
        Ingredient[] ingredients = Arrays.stream(inputs).map(CraftTweakerMC::getIngredient).toArray(Ingredient[]::new);

        recipes = new ImmaterialMediumRecipe[stones.length];
        for (int i = 0; i < callingStones.length; i++) {
            Ingredient stone = stones[i];
            recipes[i] = new ImmaterialMediumRecipe(outputStack, stone, ingredients);
        }
    }

    @Override
    public void apply() {
        for (ImmaterialMediumRecipe recipe : recipes)
            ModRecipes.register(recipe);
    }

    @Override
    public String describe() {
        if(recipes.length == 0)
            return "";
        String name = recipes[0].result.getItem().getRegistryName().toString();
        return String.format("Added medium recipe for %s", name);
    }
}
