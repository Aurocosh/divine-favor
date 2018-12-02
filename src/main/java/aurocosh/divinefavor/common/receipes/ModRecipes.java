package aurocosh.divinefavor.common.receipes;

import aurocosh.divinefavor.common.item.base.ModItems;
import aurocosh.divinefavor.common.item.symbol.ItemCallingStone;
import aurocosh.divinefavor.common.util.helper_classes.ItemStackIdComparator;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class ModRecipes {
    public static final Map<ResourceLocation,ImmaterialMediumRecipe> recipes = new HashMap<>();
    public static final Map<String,ImmaterialMediumRecipe> recipeLookup = new HashMap<>();

    public static void init() {
        register(new RecipeBuilder(new ItemStack(ModItems.arrowThrowTalisman),(ItemCallingStone) ModItems.timber_calling_stone)
                .addIngredient(Items.ARROW,8)
                .addIngredient(Items.GOLD_INGOT)
                .create()
        );
        register(new RecipeBuilder(new ItemStack(ModItems.ignition_talisman),(ItemCallingStone) ModItems.allfire_calling_stone)
                .addIngredient(Items.COAL,32)
                .addIngredient(Items.GUNPOWDER,2)
                .create()
        );

    }

    public static ImmaterialMediumRecipe findMatchingRecipe(List<ItemStack> stacks){
        String ingredientString = getStackListString(stacks);
        ImmaterialMediumRecipe recipe = recipeLookup.get(ingredientString);
        return recipe;
    }

    public static ImmaterialMediumRecipe register(ImmaterialMediumRecipe recipe) {
        recipes.put(recipe.name, recipe);

        List<ItemStack> stacks = getIngridientStacks(recipe.ingredients);
        Collections.sort(stacks,new ItemStackIdComparator());
        String ingredientString = getStackListString(stacks);
        recipeLookup.put(ingredientString,recipe);
        return recipe;
    }

    public static ItemStack getRecipeResult(List<ItemStack> stacks){
        List<ItemStack> itemStacks = new ArrayList<>(stacks);
        Collections.sort(itemStacks,new ItemStackIdComparator());
        ImmaterialMediumRecipe recipe = findMatchingRecipe(stacks);
        if(recipe == null)
            return ItemStack.EMPTY;
        return recipe.result.copy();
    }

    private static List<ItemStack> getIngridientStacks(Ingredient[] ingredients){
        List<ItemStack> stacks = new ArrayList<>();
        for (Ingredient ingredient : ingredients)
            Collections.addAll(stacks, ingredient.getMatchingStacks());
        return stacks;
    }

    private static String getStackListString(List<ItemStack> stacks){
        StringJoiner joiner = new StringJoiner("_");
        for (ItemStack itemStack : stacks){
            int id = Item.REGISTRY.getIDForObject(itemStack.getItem());
            String value = id + ":" + itemStack.getCount();
            joiner.add(value);
        }
        return joiner.toString();
    }
}
