package aurocosh.divinefavor.common.receipes

import aurocosh.divinefavor.common.lib.EmptyConst.emptyLocation
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.ResourceLocation

class ImmaterialMediumRecipe(val result: ItemStack, val callingStone: Ingredient, val ingredients: Array<Ingredient>) {
    val name: ResourceLocation
        get() = result.item.registryName ?: emptyLocation()
}