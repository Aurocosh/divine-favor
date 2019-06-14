package aurocosh.divinefavor.common.receipes.common

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.gems.ItemCallingStone
import aurocosh.divinefavor.common.lib.ItemStackIdComparator
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.process
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe
import aurocosh.divinefavor.common.receipes.RecipeExchangeInstance
import aurocosh.divinefavor.common.util.UtilHandler
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import java.util.*

object ModMediumRecipes {
    //    val recipes: ListMultimap<String, ImmaterialMediumRecipe> = MultimapBuilder.hashKeys().arrayListValues().build<String, ImmaterialMediumRecipe>()
    val recipes: MutableMap<String, ImmaterialMediumRecipe> = HashMap()

    fun init() {
    }

    fun register(recipe: ImmaterialMediumRecipe) {
        val matchingStacks = recipe.getMatchingStacks()

        val comparator = ItemStackIdComparator()
        matchingStacks.forEach { it.sortWith(comparator) }

        for (stoneIngredient in recipe.callingStones) {
            val callingStoneStack = stoneIngredient.getMatchingStacks().first()
            val callingStone = callingStoneStack.item as ItemCallingStone

            val ingredientStrings = matchingStacks.S.map { getStackListString(callingStone, it) }.toSet()
            for (ingredientString in ingredientStrings) {
                if (recipes.containsKey(ingredientString)) {
                    DivineFavor.logger.error("Recipe conflict ignoring last recipe: $ingredientString. Recipe result: ${recipe.result}")
                } else
                    recipes[ingredientString] = recipe
            }
        }
    }

    fun getRecipeResult(callingStone: ItemCallingStone, stacks: List<ItemStack>): RecipeExchangeInstance? {
        val itemStacks = stacks.sortedWith(ItemStackIdComparator())

        val ingredientString = getStackListString(callingStone, itemStacks)
        val recipe = recipes[ingredientString] ?: return null

        val minIngredientCount = itemStacks.S.map(ItemStack::getCount).min() ?: 1
        val result = recipe.result
        val resultCount = minIngredientCount * result.count;
        val stackCount = resultCount / result.maxStackSize;
        val leftover = resultCount % result.maxStackSize;

        val template = result.copy();
        template.count = template.maxStackSize

        val resultStacks = (0 until stackCount).S.map { template.copy() }.toMutableList()
        if (leftover > 0) {
            template.count = leftover
            resultStacks.add(template)
        }

        return RecipeExchangeInstance(minIngredientCount, resultStacks)
    }

    private fun getStackListString(callingStone: ItemCallingStone, stacks: List<ItemStack>): String {
        val joiner = StringJoiner("_")
        for (itemStack in stacks) {
            //            int id = Item.REGISTRY.getIDForObject(itemStack.getItem());
            val value = "${itemStack.item.registryName}:${itemStack.itemDamage}"
            joiner.add(value)
        }
        return joiner.toString() + "|" + callingStone.registryName
    }

    fun exchangeRecipe(callingStone: ItemCallingStone, stackHandler: IItemHandler, slotIndexes: List<Int>) {
        val slotStacks = UtilHandler.getNotEmptyStacksWithSlotIndexes(stackHandler)
        val stacks = slotStacks.map { (_, stack) -> stack }

        val result = getRecipeResult(callingStone, stacks)
                ?: return
        val ingredients = slotIndexes.S
                .map { stackHandler.extractItem(it, Int.MAX_VALUE, false) }
                .process { it.shrink(result.cost) }
                .filterNot(ItemStack::isEmpty)

        val validResult = (result.stacks + ingredients).take(slotIndexes.size)

        val orderQueue = LinkedList(slotIndexes)
        for (itemStack in validResult) {
            val slot = orderQueue.remove()
            stackHandler.insertItem(slot, itemStack, false)
        }
    }
}
