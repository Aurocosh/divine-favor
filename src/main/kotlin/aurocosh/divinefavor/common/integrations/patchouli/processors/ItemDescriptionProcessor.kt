package aurocosh.divinefavor.common.integrations.patchouli.processors

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.base.IDescriptionProvider
import aurocosh.divinefavor.common.item.base.ModItem
import net.minecraft.client.resources.I18n
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import vazkii.patchouli.api.IComponentProcessor
import vazkii.patchouli.api.IVariableProvider

class ItemDescriptionProcessor : IComponentProcessor {
    var text: String = ""

    override fun setup(variables: IVariableProvider<String>) {
        val itemName = variables.get("talisman")
        val item = Item.REGISTRY.getObject(ResourceLocation(itemName))
        if (item is IDescriptionProvider)
            text = I18n.format(item.descriptionKey)
        else
            DivineFavor.logger.error("Item not found:$itemName")
    }

    override fun process(key: String): String? {
        return if (key == "text") text else null
    }
}