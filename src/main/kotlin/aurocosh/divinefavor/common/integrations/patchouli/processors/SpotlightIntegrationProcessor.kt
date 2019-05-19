package aurocosh.divinefavor.common.integrations.patchouli.processors

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.registry.ModRegistries
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import vazkii.patchouli.api.IComponentProcessor
import vazkii.patchouli.api.IVariableProvider
import vazkii.patchouli.common.util.ItemStackUtil

class SpotlightIntegrationProcessor : IComponentProcessor {
    internal var item: ModItem? = null

    override fun setup(variables: IVariableProvider<String>) {
        val itemName = variables.get("item")
        item = ModRegistries.items.getValue(ResourceLocation(itemName))
        if (item == null) {
            DivineFavor.logger.error("Item not found:$itemName")
            return
        }
    }

    override fun process(key: String): String? {
        if (key.startsWith("item") && item != null) {
            val stack = ItemStack(item!!)
            return ItemStackUtil.serializeStack(stack)
        } else if (key == "name")
            return I18n.format(item!!.nameKey)
        else if (key == "text")
            return I18n.format(item!!.descriptionKey)
        return null
    }
}