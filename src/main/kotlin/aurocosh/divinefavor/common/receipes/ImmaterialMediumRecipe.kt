package aurocosh.divinefavor.common.receipes

import aurocosh.divinefavor.common.lib.EmptyConst.emptyLocation
import aurocosh.divinefavor.common.lib.extensions.S
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.ResourceLocation
import java.util.*
import kotlin.collections.ArrayList

class ImmaterialMediumRecipe(val result: ItemStack, val callingStone: Ingredient, val ingredients: List<Ingredient>) {
    val name: ResourceLocation
        get() = result.item.registryName ?: emptyLocation()

    fun getMatchingStacks(): List<MutableList<ItemStack>> {
        var prevLists = ArrayList<MutableList<ItemStack>>()
        var nextLists = ArrayList<MutableList<ItemStack>>()

        for (ingredient in ingredients) {
            val stacks = ingredient.getMatchingStacks();

            if (prevLists.isEmpty()) {
                nextLists.addAll(stacks.S.map(Collections::singletonList))
            } else {
                for (stack in stacks) {
                    val listCopies = prevLists.map { it.toMutableList() }
                    listCopies.forEach { it.add(stack) }
                    nextLists.addAll(listCopies)
                }
            }

            prevLists = nextLists
            nextLists = ArrayList()
        }

        return prevLists;
    }
}