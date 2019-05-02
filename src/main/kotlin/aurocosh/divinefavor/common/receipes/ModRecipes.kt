package aurocosh.divinefavor.common.receipes

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone
import aurocosh.divinefavor.common.lib.ItemStackIdComparator
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient

import java.util.*

object ModRecipes {
    val recipes: MutableMap<String, ImmaterialMediumRecipe> = HashMap()

    fun init() {
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

    fun register(recipe: ImmaterialMediumRecipe) {
        val stacks = getIngridientStacks(recipe.ingredients)
        stacks.sortWith(ItemStackIdComparator())
        val callingStoneStack = recipe.callingStone.matchingStacks[0]
        val callingStone = callingStoneStack.item as ItemCallingStone
        val ingredientString = getStackListString(callingStone, stacks)
        if (recipes.containsKey(ingredientString)) {
            DivineFavor.logger.error("Recipe conflict ignoring last recipe: " + ingredientString + ". Recipe result: " + recipe.result.toString())
        } else
            recipes[ingredientString] = recipe
    }

    fun getRecipeResult(callingStone: ItemCallingStone, stacks: List<ItemStack>): ItemStack {
        val itemStacks = ArrayList(stacks)
        itemStacks.sortWith(ItemStackIdComparator())

        val ingredientString = getStackListString(callingStone, itemStacks)
        val recipe = recipes[ingredientString] ?: return ItemStack.EMPTY

        return recipe.result.copy()
    }

    private fun getIngridientStacks(ingredients: Array<Ingredient>): ArrayList<ItemStack> {
        val stacks = ArrayList<ItemStack>()
        for (ingredient in ingredients)
            Collections.addAll(stacks, *ingredient.matchingStacks)
        return stacks
    }

    private fun getStackListString(callingStone: ItemCallingStone, stacks: List<ItemStack>): String {
        val joiner = StringJoiner("_")
        for (itemStack in stacks) {
            //            int id = Item.REGISTRY.getIDForObject(itemStack.getItem());
            val value = itemStack.item.registryName.toString() + ":" + itemStack.itemDamage + ":" + itemStack.count
            joiner.add(value)
        }
        return joiner.toString() + "|" + callingStone.registryName
    }
}
