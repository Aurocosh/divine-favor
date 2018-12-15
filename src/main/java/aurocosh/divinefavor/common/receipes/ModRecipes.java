package aurocosh.divinefavor.common.receipes;

import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.item.talismans.ModTalismans;
import aurocosh.divinefavor.common.lib.ItemStackIdComparator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class ModRecipes {
    public static final Map<ResourceLocation, ImmaterialMediumRecipe> recipes = new HashMap<>();
    public static final Map<String, ImmaterialMediumRecipe> recipeLookup = new HashMap<>();

    public static void init() {
        register(new RecipeBuilder(new ItemStack(ModTalismans.arrowThrowTalisman), (ItemCallingStone) ModCallingStones.calling_stone_timber)
                .addIngredient(Items.ARROW, 8)
                .addIngredient(Items.GOLD_INGOT)
                .create()
        );
        register(new RecipeBuilder(new ItemStack(ModTalismans.ignition), (ItemCallingStone) ModCallingStones.calling_stone_allfire)
                .addIngredient(Items.COAL, 32)
                .addIngredient(Items.GUNPOWDER, 2)
                .create()
        );
    }

    public static ImmaterialMediumRecipe register(ImmaterialMediumRecipe recipe) {
        recipes.put(recipe.name, recipe);

        List<ItemStack> stacks = getIngridientStacks(recipe.ingredients);
        stacks.sort(new ItemStackIdComparator());
        ItemStack callingStone = recipe.callingStone.getMatchingStacks()[0];
        String ingredientString = getStackListString(callingStone, stacks);
        recipeLookup.put(ingredientString, recipe);
        return recipe;
    }

    public static ItemStack getRecipeResult(ItemStack callingStone, List<ItemStack> stacks) {
        List<ItemStack> itemStacks = new ArrayList<>(stacks);
        itemStacks.sort(new ItemStackIdComparator());

        String ingredientString = getStackListString(callingStone, stacks);
        ImmaterialMediumRecipe recipe = recipeLookup.get(ingredientString);

        if (recipe == null)
            return ItemStack.EMPTY;
        return recipe.result.copy();
    }

    private static List<ItemStack> getIngridientStacks(Ingredient[] ingredients) {
        List<ItemStack> stacks = new ArrayList<>();
        for (Ingredient ingredient : ingredients)
            Collections.addAll(stacks, ingredient.getMatchingStacks());
        return stacks;
    }

    private static String getStackListString(ItemStack callingStone, List<ItemStack> stacks) {
        StringJoiner joiner = new StringJoiner("_");
        for (ItemStack itemStack : stacks) {
//            int id = Item.REGISTRY.getIDForObject(itemStack.getItem());
            String value = itemStack.getItem().getRegistryName() + ":" + itemStack.getCount();
            joiner.add(value);
        }
        return joiner.toString() + "|" + callingStone.getItem().getRegistryName();
    }
}
