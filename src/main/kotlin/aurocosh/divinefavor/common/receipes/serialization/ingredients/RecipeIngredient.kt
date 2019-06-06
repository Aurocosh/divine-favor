package aurocosh.divinefavor.common.receipes.serialization.ingredients

import net.minecraft.item.crafting.Ingredient

abstract class RecipeIngredient {
    abstract fun toIngredient(): Ingredient?
}
