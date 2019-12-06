package aurocosh.divinefavor.common.receipes.common

import aurocosh.divinefavor.common.receipes.base.ModRecipe
import aurocosh.divinefavor.common.receipes.dynamic.GemMaskRecipe
import aurocosh.divinefavor.common.receipes.dynamic.GemStabilizerRecipe

object ModRecipes {
    lateinit var gem_stabilizer_recipe: ModRecipe
    lateinit var gem_mask_recipe: ModRecipe

    fun preInit() {
        gem_stabilizer_recipe = GemStabilizerRecipe()
        gem_mask_recipe = GemMaskRecipe()
    }

    fun init() {
    }
}