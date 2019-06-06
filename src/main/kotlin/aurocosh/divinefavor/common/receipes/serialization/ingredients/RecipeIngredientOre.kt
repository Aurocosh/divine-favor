package aurocosh.divinefavor.common.receipes.serialization.ingredients

import aurocosh.divinefavor.DivineFavor
import net.minecraft.item.crafting.Ingredient
import net.minecraftforge.oredict.OreDictionary
import net.minecraftforge.oredict.OreIngredient

class RecipeIngredientOre : RecipeIngredient() {
    val ore: String = "gemDiamond"

    override fun toIngredient(): Ingredient? {
        return if (!OreDictionary.doesOreNameExist(ore)) {
            DivineFavor.logger.error("Recipe ingredient not found:$ore")
            null
        } else
            OreIngredient(ore)
    }
}
