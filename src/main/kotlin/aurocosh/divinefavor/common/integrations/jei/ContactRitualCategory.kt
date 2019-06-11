package aurocosh.divinefavor.common.integrations.jei

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstResources
import mezz.jei.api.IGuiHelper
import mezz.jei.api.gui.IDrawable
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.IRecipeCategory
import net.minecraft.client.resources.I18n
import net.minecraft.util.ResourceLocation

class ContactRitualCategory(helper: IGuiHelper) : IRecipeCategory<ContactRitualWrapper> {
    private val background: IDrawable

    init {
        this.background = helper.createDrawable(ResourceLocation(ConstResources.GUI_JEI_CONTACT_RITUAL_RECIPE), 0, 0, 146, 86)
    }

    override fun getUid(): String {
        return DivineFavorJEIPlugin.CONTACT_RITUAL_UID
    }

    override fun getTitle(): String {
        return I18n.format("container." + DivineFavorJEIPlugin.CONTACT_RITUAL_UID + ".name")
    }

    override fun getModName(): String {
        return DivineFavor.MOD_NAME
    }

    override fun getBackground(): IDrawable {
        return this.background
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: ContactRitualWrapper, ingredients: IIngredients) {
        val group = recipeLayout.itemStacks
        val recipe = recipeWrapper.recipe

        group.init(0, true, 19, 34)
        group.set(0, recipe.blend.getMatchingStacks().asList())

        group.init(1, false, 60, 34)
        group.set(1, recipe.markedGlass.getMatchingStacks().asList())

        group.init(2, false, 109, 34)
        group.set(2, recipe.result)
    }
}