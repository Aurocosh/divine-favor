package aurocosh.divinefavor.common.receipes;

import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilder {
    public ItemStack result;
    private Ingredient callingStone;
    private List<Ingredient> ingredients;

    public RecipeBuilder(ItemStack result, ItemCallingStone callingStone) {
        this.result = result;
        this.callingStone = Ingredient.fromStacks(new ItemStack(callingStone));
        ingredients = new ArrayList<>();
    }

    public RecipeBuilder(ItemStack result, ItemCallingStone callingStone, List<ItemStack> ingredientStacks) {
        this(result, callingStone);
        for (ItemStack stack : ingredientStacks)
            ingredients.add(Ingredient.fromStacks(stack));
    }

    public RecipeBuilder addIngredient(Item item, int count) {
        ingredients.add(Ingredient.fromStacks(new ItemStack(item, count)));
        return this;
    }

    public RecipeBuilder addIngredient(Item item) {
        ingredients.add(Ingredient.fromItems(item));
        return this;
    }

    public ImmaterialMediumRecipe create() {
        Ingredient[] ingredientsArray = new Ingredient[ingredients.size()];
        ingredientsArray = ingredients.toArray(ingredientsArray);

        return new ImmaterialMediumRecipe(result, callingStone, ingredientsArray);
    }
}
