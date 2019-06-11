package aurocosh.divinefavor.common.receipes.common

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.bathing_blend.ModBathingBlends
import aurocosh.divinefavor.common.item.bathing_blend.base.ItemBathingBlend
import aurocosh.divinefavor.common.item.common.ModCallingStones
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.gems.ItemCallingStone
import aurocosh.divinefavor.common.item.gems.ItemMarkedGlass
import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.extensions.regName
import aurocosh.divinefavor.common.receipes.ContactRitualRecipe
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import kotlin.collections.ArrayList

object ModContactRecipes {
    val recipes: MutableList<ContactRitualRecipe> = ArrayList()

    fun init() {
        val glassMap = ModItems.markedGlasses.S.map { it.spirit to it }.toMap()

        register(ModCallingStones.calling_stone_arbow, ModBathingBlends.feathers, glassMap)
        register(ModCallingStones.calling_stone_blizrabi, ModBathingBlends.snow, glassMap)
        register(ModCallingStones.calling_stone_endererer, ModBathingBlends.ender_pearl, glassMap)
        register(ModCallingStones.calling_stone_loon, ModBathingBlends.lapis, glassMap)
        register(ModCallingStones.calling_stone_neblaze, ModBathingBlends.charcoal, glassMap)
        register(ModCallingStones.calling_stone_redwind, ModBathingBlends.redstone, glassMap)
        register(ModCallingStones.calling_stone_romol, ModBathingBlends.flint, glassMap)
        register(ModCallingStones.calling_stone_squarefury, ModBathingBlends.fleshy, glassMap)
        register(ModCallingStones.calling_stone_timber, ModBathingBlends.wood, glassMap)
    }

    fun register(callingStone: ItemCallingStone, blend: ItemBathingBlend, glassMap: Map<ModSpirit, ItemMarkedGlass>) {
        val markedGlass = glassMap[callingStone.spirit]
        if (markedGlass == null) {
            DivineFavor.logger.error("Marked glass needed for spirit ${callingStone.spirit.regName} not found")
            return
        }

        val result = ItemStack(callingStone)
        val blendIngredient = Ingredient.fromItem(blend)
        val glassIngredient = Ingredient.fromItem(markedGlass)
        recipes.add(ContactRitualRecipe(result, blendIngredient, glassIngredient))
    }
}
