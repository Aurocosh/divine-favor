package aurocosh.divinefavor.common.integrations.jei

import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.base.ModItemBlock
import aurocosh.divinefavor.common.receipes.ImmaterialMediumRecipe
import aurocosh.divinefavor.common.receipes.ModRecipes
import aurocosh.divinefavor.common.registry.ModRegistries
import mezz.jei.api.IGuiHelper
import mezz.jei.api.IModPlugin
import mezz.jei.api.IModRegistry
import mezz.jei.api.JEIPlugin
import mezz.jei.api.ingredients.VanillaTypes
import mezz.jei.api.recipe.IRecipeCategoryRegistration
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary

@JEIPlugin
class DivineFavorJEIPlugin : IModPlugin {
    override fun registerCategories(registry: IRecipeCategoryRegistration) {
        val helper = registry.jeiHelpers.guiHelper
        registry.addRecipeCategories(ImmaterialMediumCategory(helper))
    }

    override fun register(registry: IModRegistry?) {
        registerDescription(registry)
        registerRecipes(registry!!)
    }

    private fun registerRecipes(registry: IModRegistry) {
        registry.handleRecipes(ImmaterialMediumRecipe::class.java, { ImmaterialMediumWrapper(it) }, IMMATERIAL_MEDIUM_UID)
        registry.addRecipes(ModRecipes.recipes.values, IMMATERIAL_MEDIUM_UID)
        registry.addRecipeCatalyst(ItemStack(ModBlocks.medium_iron), IMMATERIAL_MEDIUM_UID)
        registry.addRecipeCatalyst(ItemStack(ModBlocks.medium_gold), IMMATERIAL_MEDIUM_UID)
        registry.addRecipeCatalyst(ItemStack(ModBlocks.medium_lapis), IMMATERIAL_MEDIUM_UID)
        registry.addRecipeCatalyst(ItemStack(ModBlocks.medium_log), IMMATERIAL_MEDIUM_UID)
        registry.addRecipeCatalyst(ItemStack(ModBlocks.medium_obsidian), IMMATERIAL_MEDIUM_UID)
        registry.addRecipeCatalyst(ItemStack(ModBlocks.medium_redstone), IMMATERIAL_MEDIUM_UID)
    }

    private fun registerDescription(registry: IModRegistry?) {
        for (item in ModRegistries.items.values)
            registry!!.addIngredientInfo(ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), VanillaTypes.ITEM, item.descriptionKey)
        for (itemBlock in ModRegistries.itemBlocks.values)
            registry!!.addIngredientInfo(ItemStack(itemBlock, 1, OreDictionary.WILDCARD_VALUE), VanillaTypes.ITEM, itemBlock.descriptionKey)
    }

    companion object {
        val IMMATERIAL_MEDIUM_UID = ResourceNamer.getFullName("immaterial_medium").toString()
    }
}