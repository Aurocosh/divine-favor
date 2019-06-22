package aurocosh.divinefavor.common.receipes.common

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.gems.ItemCallingStone
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.process
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe
import aurocosh.divinefavor.common.receipes.RecipeExchangeInstance
import aurocosh.divinefavor.common.util.UtilHandler
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import java.util.*

object ModMediumRecipes {
    val recipes = ArrayList<ImmaterialMediumRecipe>()

    fun init() {
    }

    fun postInit() {
        checkForRecipeConflicts()
    }

    private fun checkForRecipeConflicts() {
        val conflictMap = HashMap<String, String>()
        for (recipe in recipes) {
            val result = recipe.result.toString()
            val stringId = recipe.getStringId()
            if (conflictMap.containsKey(stringId))
                DivineFavor.logger.error("Recipe conflict for item: $result. Same recipe already used for: ${conflictMap[stringId]}.")
            else
                conflictMap[stringId] = result
        }
    }

    fun register(recipe: ImmaterialMediumRecipe) {
        recipes.add(recipe)
    }

    private fun getRecipeResult(callingStone: ItemCallingStone, stacks: List<ItemStack>): RecipeExchangeInstance? {
        val recipe = recipes.firstOrNull { it.isMatching(callingStone, stacks) } ?: return null

        val minIngredientCount = stacks.S.map(ItemStack::getCount).min() ?: 1
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
