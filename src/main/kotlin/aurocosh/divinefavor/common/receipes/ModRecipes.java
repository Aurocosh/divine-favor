package aurocosh.divinefavor.common.receipes;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone;
import aurocosh.divinefavor.common.lib.ItemStackIdComparator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.*;

public class ModRecipes {
    public static final List<ImmaterialMediumRecipe> recipesToAdd = new ArrayList<>();
    public static final List<ImmaterialMediumRecipe> recipesToDelete = new ArrayList<>();

    public static final Map<String, ImmaterialMediumRecipe> recipes = new HashMap<>();

    public static void init() {
//        register(new RecipeBuilder(new ItemStack(ModSpellTalismans.arrow_throw_talisman), ModCallingStones.calling_stone_timber)
//                .addIngredient(Items.ARROW, 8)
//                .addIngredient(Items.GOLD_INGOT)
//                .create()
//        );
//        register(new RecipeBuilder(new ItemStack(ModSpellTalismans.ignition), ModCallingStones.calling_stone_neblaze)
//                .addIngredient(Items.COAL, 32)
//                .addIngredient(Items.GUNPOWDER, 2)
//                .create()
//        );
    }

    public static void register(ImmaterialMediumRecipe recipe) {
        List<ItemStack> stacks = getIngridientStacks(recipe.ingredients);
        stacks.sort(new ItemStackIdComparator());
        ItemStack callingStoneStack = recipe.callingStone.getMatchingStacks()[0];
        ItemCallingStone callingStone = (ItemCallingStone) callingStoneStack.getItem();
        String ingredientString = getStackListString(callingStone, stacks);
        if(recipes.containsKey(ingredientString)){
            DivineFavor.logger.error("Recipe conflict ignoring last recipe: " + ingredientString + ". Recipe result: " + recipe.result.toString());
        }
        else
            recipes.put(ingredientString, recipe);
    }

    public static ItemStack getRecipeResult(ItemCallingStone callingStone, List<ItemStack> stacks) {
        List<ItemStack> itemStacks = new ArrayList<>(stacks);
        itemStacks.sort(new ItemStackIdComparator());

        String ingredientString = getStackListString(callingStone, itemStacks);
        ImmaterialMediumRecipe recipe = recipes.get(ingredientString);

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

    private static String getStackListString(ItemCallingStone callingStone, List<ItemStack> stacks) {
        StringJoiner joiner = new StringJoiner("_");
        for (ItemStack itemStack : stacks) {
//            int id = Item.REGISTRY.getIDForObject(itemStack.getItem());
            String value = itemStack.getItem().getRegistryName() + ":" + itemStack.getItemDamage() + ":" + itemStack.getCount();
            joiner.add(value);
        }
        return joiner.toString() + "|" + callingStone.getRegistryName();
    }
}
