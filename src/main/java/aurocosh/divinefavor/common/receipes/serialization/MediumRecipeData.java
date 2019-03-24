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
    public RecipeIngredient result;
    public List<RecipeIngredient> ingredients;

    public MediumRecipeData() {
        stone = "divinefavor:calling_stone_neblaze";
        result = null;
        ingredients = new ArrayList<>();
    }

    public ImmaterialMediumRecipe toRecipe() {
        ItemStack resultStack = result.toItemStack();
        if (resultStack.isEmpty()) {
            DivineFavor.logger.error("Recipe error: " + result.item + ". Result is invalid:" + result.item);
            return null;
        }

        Item stoneItem = Item.getByNameOrId(stone);
        if (!(stoneItem instanceof ItemCallingStone)) {
            DivineFavor.logger.error("Recipe error: " + result.item + ". Item is not a calling stone:" + stone);
            return null;
        }
        ItemCallingStone callingStone = (ItemCallingStone) stoneItem;

        List<ItemStack> ingredientStacks = new ArrayList<>();
        for (RecipeIngredient ingredient : ingredients) {
            ItemStack itemStack = ingredient.toItemStack();
            if (itemStack.isEmpty()) {
                DivineFavor.logger.error("Recipe error: " + result.item + ". Ingredient is invalid:" + ingredient.item);
                return null;
            }
            ingredientStacks.add(itemStack);
        }

        return new RecipeBuilder(resultStack, callingStone, ingredientStacks).create();
    }
}
