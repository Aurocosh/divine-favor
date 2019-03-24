package aurocosh.divinefavor.common.receipes.serialization;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone;
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe;
import aurocosh.divinefavor.common.receipes.RecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MediumRecipeData {
    public String stone;
    public List<String> stones;
    public RecipeIngredient result;
    public List<RecipeIngredient> ingredients;

    public List<ImmaterialMediumRecipe> toRecipes() {
        ItemStack resultStack = result.toItemStack();
        if (resultStack.isEmpty()) {
            DivineFavor.logger.error("Recipe error: " + result.item + ". Result is invalid:" + result.item);
            return null;
        }

        List<ItemCallingStone> callingStones = new ArrayList<>();
        if (stone != null) {
            ItemCallingStone stoneItem = getCallingStone(stone);
            if (stoneItem != null)
                callingStones.add(stoneItem);
        }
        else if (stones != null) {
            for (String stoneName : stones) {
                ItemCallingStone stoneItem = getCallingStone(stoneName);
                if (stoneItem != null)
                    callingStones.add(stoneItem);
            }
        }

        if (callingStones.isEmpty()) {
            DivineFavor.logger.error("Recipe error: " + result.item + ". No calling stones defined.");
            return null;
        }

        List<ItemStack> ingredientStacks = new ArrayList<>();
        for (RecipeIngredient ingredient : ingredients) {
            ItemStack itemStack = ingredient.toItemStack();
            if (itemStack.isEmpty()) {
                DivineFavor.logger.error("Recipe error: " + result.item + ". Ingredient is invalid:" + ingredient.item);
                return null;
            }
            ingredientStacks.add(itemStack);
        }

        List<ImmaterialMediumRecipe> recipeList = new ArrayList<>();
        for (ItemCallingStone callingStone : callingStones)
            recipeList.add(new RecipeBuilder(resultStack, callingStone, ingredientStacks).create());
        return recipeList;
    }

    private ItemCallingStone getCallingStone(String stone) {
        Item stoneItem = Item.getByNameOrId(stone);
        if (!(stoneItem instanceof ItemCallingStone)) {
            DivineFavor.logger.error("Recipe error: " + result.item + ". Item is not a calling stone:" + stone);
            return null;
        }
        return (ItemCallingStone) stoneItem;
    }
}
