package aurocosh.divinefavor.common.integrations.jei

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstResources
import aurocosh.divinefavor.common.item.common.ModItems
import mezz.jei.api.IGuiHelper
import mezz.jei.api.gui.IDrawable
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.IRecipeCategory
import net.minecraft.client.resources.I18n
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.ResourceLocation

class ImmaterialMediumCategory(helper: IGuiHelper) : IRecipeCategory<ImmaterialMediumWrapper> {
    private val background: IDrawable

    init {
        this.background = helper.createDrawable(ResourceLocation(ConstResources.GUI_JEI_IMMATERIAL_MEDIUM_RECIPE), 0, 0, 146, 86)
    }

    override fun getUid(): String {
        return DivineFavorJEIPlugin.IMMATERIAL_MEDIUM_UID
    }

    override fun getTitle(): String {
        return I18n.format("container." + DivineFavorJEIPlugin.IMMATERIAL_MEDIUM_UID + ".name")
    }

    override fun getModName(): String {
        return DivineFavor.MOD_NAME
    }

    override fun getBackground(): IDrawable {
        return this.background
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: ImmaterialMediumWrapper, ingredients: IIngredients) {
        val group = recipeLayout.itemStacks
        val recipe = recipeWrapper.recipe

        group.init(0, false, 64, 34)
        group.set(0, Ingredient.fromItem(ModItems.ritual_pouch).getMatchingStacks().asList())

        group.init(1, false, 91, 34)
        group.set(1, recipe.callingStones.flatMap { it.getMatchingStacks().asList() })

        group.init(2, false, 124, 34)
        group.set(2, recipe.result)

        val positions = arrayOf(intArrayOf(7, 17), //Top left
                intArrayOf(25, 17), //Top center
                intArrayOf(43, 17), //Top right
                intArrayOf(25, 35), //Center
                intArrayOf(7, 53), //Bottom left
                intArrayOf(25, 53), //Bottom center
                intArrayOf(43, 53))//Bottom right
        for (i in 0 until recipe.ingredients.size) {
            group.init(i + 3, true, positions[i][0] - 1, positions[i][1] - 1)
            group.set(i + 3, recipe.ingredients[i].getMatchingStacks().asList())
        }
    }
}