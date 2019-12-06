package aurocosh.divinefavor.common.receipes.common

import aurocosh.divinefavor.common.receipes.base.ModRecipe
import aurocosh.divinefavor.common.receipes.dynamic.GemMaskRecipe
import aurocosh.divinefavor.common.receipes.dynamic.GemStabilizerRecipe
import aurocosh.divinefavor.common.receipes.dynamic.PebbleStabilizerRecipe

object ModRecipes {
    lateinit var pebble_stabilizer_recipe: ModRecipe
    lateinit var gem_stabilizer_recipe: ModRecipe
    lateinit var gem_mask_recipe: ModRecipe

    fun preInit() {
        pebble_stabilizer_recipe = PebbleStabilizerRecipe()
        gem_stabilizer_recipe = GemStabilizerRecipe()
        gem_mask_recipe = GemMaskRecipe()
    }

    fun init() {
    }
}